package de.digiwill.service;

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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class RegistrationService {
    private static final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final int minimumAge = 13;
    private static final DateFormat df = new SimpleDateFormat("yyyy-dd-MM");

    @Autowired
    private UserHandleManager userHandleManager;

    public RegistrationResponse addNewUser(MultiValueMap<String, String> formData) {

        if(formData == null){
            return RegistrationResponse.FORM_DATA_DOESNT_EXIST;
        }

        if (doPasswordsMatch(formData)) {
            return RegistrationResponse.PASSWORD_MISMATCH;
        }
        if (!doesPasswordFitRequirements(formData.getFirst("password"))) {
            return RegistrationResponse.PASSWORD_REQUIREMENTS_NOT_MET;
        }
        if (!isValidEmailAddress(formData.getFirst("email"))) {
            return RegistrationResponse.INVALID_EMAIL_ADDRESS;
        }
        if (formData.getFirst("firstName").length() == 0) {
            return RegistrationResponse.NO_FIRST_NAME;
        }
        if (formData.getFirst("surName").length() == 0) {
            return RegistrationResponse.NO_SURNAME;
        }

        Date birthdayDate;
        try {
            birthdayDate = df.parse(formData.getFirst("birthday"));
        } catch (ParseException e) {
            return RegistrationResponse.BIRTHDAY_INVALID;
        }
        if (!isOldEnough(birthdayDate)) {
            return RegistrationResponse.TO_YOUNG;
        }
        try {
            UserHandle uH = userHandleManager.loadUserByEmailAddress(formData.getFirst("email"));
            if (uH != null) {
                return RegistrationResponse.EMAIL_ALREADY_IN_USE;
            }
        } catch (UsernameNotFoundException ignore) {}

        userHandleManager.createUser(generateUserHandleFromFormData(formData, birthdayDate));
        return RegistrationResponse.REGISTRATION_SUCCESSFUL;
    }

    private boolean doPasswordsMatch(MultiValueMap<String, String> formData) {
        return !formData.getFirst("password").equals(formData.getFirst("password_rep"));
    }

    private UserHandle generateUserHandleFromFormData(MultiValueMap<String, String> formData, Date birthdayDate) {
        PersonalData personalData = new PersonalData(formData.getFirst("firstName"),
                formData.getFirst("surName"),
                birthdayDate);
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");

        UserHandle userHandle = new UserHandle(formData.getFirst("email"),
                SecurityHelper.encodePassword(formData.getFirst("password")),
                personalData,
                authorities);
        userHandle.sendLifeSign();
        userHandle.setDeltaDeathTime(60*60*24*14); //Sets time to 14 days
        return userHandle;
    }

    private boolean isOldEnough(Date date) {
        //TODO replace with DateTime implementation
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.add(Calendar.YEAR, -minimumAge);
        return today.getTime().compareTo(date) >= 0;
    }

    public boolean doesPasswordFitRequirements(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=ß!\"§~'`<>|;:,.\\-\\\\\\/\\?])(?=\\S+$).{8,}$");
    }

    public boolean isValidEmailAddress(String email) {
        return email.matches(emailRegex);
    }
}
