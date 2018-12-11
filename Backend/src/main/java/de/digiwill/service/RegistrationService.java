package de.digiwill.service;

import de.digiwill.controller.RegisterController;
import de.digiwill.model.PersonalData;
import de.digiwill.model.Security.SecurityHelper;
import de.digiwill.model.UserHandle;
import de.digiwill.model.UserHandleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final int REGISTRATION_SUCCESSFUL = 0;
    private static final int PASSWORD_MISMATCH = -1;
    private static final int PASSWORD_REQUIREMENTS_NOT_MET = -2;
    private static final int INVALID_EMAIL_ADDRESS = -3;
    private static final int NO_FIRST_NAME = -4;
    private static final int NO_SURNAME = -5;
    private static final int TO_YOUNG = -6;
    private static final int EMAIL_ALREADY_IN_USE = -7;
    private static final int BIRTHDAY_INVALID = -8;

    private static final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final int minimumAge = 13;
    private static final DateFormat df = new SimpleDateFormat("yyyy-dd-MM");

    @Autowired
    UserHandleManager userHandleManager;

    Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    public int addNewUser(MultiValueMap<String, String> formData) {


        if (!formData.getFirst("password").equals(formData.getFirst("password_rep"))) {
            return PASSWORD_MISMATCH;
        }
        if (!doesPasswordFitRequirements(formData.getFirst("password"))) {
            return PASSWORD_REQUIREMENTS_NOT_MET;
        }
        if (!isValidEmailAdress(formData.getFirst("email"))) {
            return INVALID_EMAIL_ADDRESS;
        }
        if (formData.getFirst("firstName").length() == 0) {
            return NO_FIRST_NAME;
        }
        if (formData.getFirst("surName").length() == 0) {
            return NO_SURNAME;
        }

        Date birthdayDate;
        try {
            birthdayDate = df.parse(formData.getFirst("birthday"));
        } catch (ParseException e) {
            return BIRTHDAY_INVALID;
        }
        if (!isOldEnough(birthdayDate)) {
            return TO_YOUNG;
        }
        try {
            UserHandle uH = (UserHandle) userHandleManager.loadUserByUsername(formData.getFirst("email"));
            if (uH != null) {
                return EMAIL_ALREADY_IN_USE;
            }
        } catch (UsernameNotFoundException e) {
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
        userHandle.setDeltaDeathTime(60*60*24*14); //Sets time to 14 days

        userHandleManager.createUser(userHandle);
        return REGISTRATION_SUCCESSFUL;
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

    public boolean isValidEmailAdress(String email) {
        return email.matches(emailRegex);
    }

    public boolean wasRegistrationSuccesful(int returnCode) {
        return returnCode == REGISTRATION_SUCCESSFUL;
    }

    public String codeToText(int returnCode) {
        switch (returnCode) {
            case REGISTRATION_SUCCESSFUL:
                return "Everything went great, you shouldn't be seeing this";
            case PASSWORD_MISMATCH:
                return "The entered passwords don't match";
            case PASSWORD_REQUIREMENTS_NOT_MET:
                return "The password requirements weren't met";
            case INVALID_EMAIL_ADDRESS:
                return "Please enter a valid email address";
            case NO_FIRST_NAME:
                return "Please enter a first name";
            case NO_SURNAME:
                return "Please enter a surname";
            case TO_YOUNG:
                return "You must be at least XXX years old to register for this service";
            case EMAIL_ALREADY_IN_USE:
                return "There already is an account registered with the email address you're trying to use";
            case BIRTHDAY_INVALID:
                return "Please enter a valid date of birth";
        }
        return "Not implemented yet, please contact the support team and let them know you experience return code "
                + returnCode + " during registration or try again";
    }
}
