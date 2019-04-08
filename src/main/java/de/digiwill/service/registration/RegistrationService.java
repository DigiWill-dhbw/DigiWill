package de.digiwill.service.registration;

import de.digiwill.model.PersonalData;
import de.digiwill.util.SecurityHelper;
import de.digiwill.model.UserHandle;
import de.digiwill.model.UserHandleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RegistrationService {
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");

    @Autowired
    private UserHandleManager userHandleManager;

    private List<RegistrationValidator> validators = new ArrayList<>();

    public RegistrationService() {
        validators.add(new PasswordMatchValidator());
        validators.add(new PasswordRequirementValidator());
        validators.add(new EmailAddressValidator());
        validators.add(new NonEmptyStringValidator("firstName", RegistrationResponse.NO_FIRST_NAME));
        validators.add(new NonEmptyStringValidator("surName", RegistrationResponse.NO_SURNAME));
        validators.add(new BirthdayValidator(dateFormat));
        validators.add(new EmailAddressNotInUseValidator(userHandleManager));
    }

    public RegistrationResponse addNewUser(final MultiValueMap<String, String> formData) {

        if (formData == null) {
            return RegistrationResponse.FORM_DATA_DOESNT_EXIST;
        }
        for (RegistrationValidator validator : validators) {
            if (!validator.validate(formData)) {
                return validator.getResponse();
            }
        }

        userHandleManager.createUser(generateUserHandleFromFormData(formData));

        return RegistrationResponse.REGISTRATION_SUCCESSFUL;
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
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

        UserHandle userHandle = new UserHandle(formData.getFirst("email"),
                SecurityHelper.encodePassword(formData.getFirst("password")),
                personalData,
                authorities);
        userHandle.sendLifeSign();
        userHandle.setDeltaDeathTime(60 * 60 * 24 * 14); //Sets time to 14 days
        return userHandle;
    }
}
