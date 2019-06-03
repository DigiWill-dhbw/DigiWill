package de.digiwill.service;

import de.digiwill.exception.EmailException;
import de.digiwill.model.*;
import de.digiwill.repository.EmailResponseHandleRepository;
import de.digiwill.service.validation.*;
import de.digiwill.util.SecurityHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RegistrationService {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    @Autowired
    private UserHandleManager userHandleManager;
    @Autowired
    private EmailDispatcher emailDispatcher;
    @Autowired
    private EmailResponseHandleRepository emailResponseHandleRepository;

    private List<Validator> validators = new ArrayList<>();

    public RegistrationService() {
        validators.add(new PasswordMatchValidator());
        validators.add(new PasswordRequirementValidator());
        validators.add(new EmailAddressValidator());
        validators.add(new NonEmptyStringValidator("firstName", ValidationResponse.NO_FIRST_NAME));
        validators.add(new NonEmptyStringValidator("surName", ValidationResponse.NO_SURNAME));
        validators.add(new BirthdayValidator(dateFormat));
    }

    public ValidationResponse addNewUser(final MultiValueMap<String, String> formData) {

        if (formData == null) {
            return ValidationResponse.FORM_DATA_DOESNT_EXIST;
        }

        try {
            userHandleManager.loadUserByEmailAddress(formData.getFirst("email"));
            return ValidationResponse.EMAIL_ALREADY_IN_USE;
        }catch(UsernameNotFoundException ignore){}

        for (Validator validator : validators) {
            if (!validator.validate(formData)) {
                return validator.getResponse();
            }
        }

        UserHandle userHandle = generateUserHandleFromFormData(formData);
        try {
            userHandleManager.createUser(userHandle);

            EmailVerificationHandle emailVerificationHandle = new EmailVerificationHandle(userHandle);
            emailResponseHandleRepository.insert(emailVerificationHandle);

            emailDispatcher.sendRegistrationConfirmationEmail(emailVerificationHandle, userHandle);
        }catch(EmailException e){
            userHandleManager.deleteUser(userHandle.getEmailAddress());
            logger.error(e.getMessage());
            return ValidationResponse.INTERNAL_ERROR;
        }

        return ValidationResponse.SUCCESSFUL;
    }

    private UserHandle generateUserHandleFromFormData(MultiValueMap<String, String> formData) {
        Date birthdayDate = null;
        try {
            birthdayDate = dateFormat.parse(formData.getFirst("birthday"));
        } catch (ParseException ignored) {
        }

        PersonalData personalData = new PersonalData(formData.getFirst("firstName"),
                formData.getFirst("surName"),
                birthdayDate);
        AuthoritySet authorities = new AuthoritySet(AuthorityUtils.createAuthorityList("ROLE_USER"));

        UserHandle userHandle = UserHandleFactory.createUserHandleWithEmailPasswordAuthoritiesPersonalData(
                formData.getFirst("email"),
                SecurityHelper.encodePassword(formData.getFirst("password")),
                personalData,
                authorities);
        userHandle.sendSignOfLife();
        userHandle.setDeltaDeathTime(60 * 60 * 24 * 14); //Sets time to 14 days
        return userHandle;
    }
}
