package de.digiwill.service;

import de.digiwill.model.Address;
import de.digiwill.model.PersonalData;
import de.digiwill.model.UserHandle;
import de.digiwill.service.validation.BirthdayValidator;
import de.digiwill.service.validation.NonEmptyStringValidator;
import de.digiwill.service.validation.ValidationResponse;
import de.digiwill.service.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private UserHandleManager userHandleManager;

    private List<Validator> validators;

    public ProfileService() {
        validators = new ArrayList<>();
        validators.add(new NonEmptyStringValidator("firstNameInput", ValidationResponse.NO_FIRST_NAME));
        validators.add(new NonEmptyStringValidator("surNameInput", ValidationResponse.NO_SURNAME));
        validators.add(new BirthdayValidator());
    }

    public UserHandle getUserHandleByEmail(String email) {
        return userHandleManager.loadUserByEmailAddress(email);
    }

    public ValidationResponse editUser(final MultiValueMap<String, String> formData, final String email) {
        if (formData == null) {
            return ValidationResponse.FORM_DATA_DOESNT_EXIST;
        }

        for (Validator validator : validators) {
            if (!validator.validate(formData)) {
                return validator.getResponse();
            }
        }

        UserHandle existingUser;
        try {
            existingUser = userHandleManager.loadUserByEmailAddress(email);
        } catch (UsernameNotFoundException e) {
            return ValidationResponse.INTERNAL_ERROR;
        }

        Address address = new Address(formData.getFirst("zipCodeInput"), formData.getFirst("cityInput"),
                formData.getFirst("streetAddressInput"), formData.getFirst("countryInput"));
        try {
            PersonalData personalData = new PersonalData(formData.getFirst("firstNameInput"),
                    formData.getFirst("surNameInput"), BirthdayValidator.DATE_FORMAT.parse(formData.getFirst("birthday")), address);
            existingUser.setPersonalData(personalData);
            userHandleManager.updateUser(existingUser);
            return ValidationResponse.SUCCESSFUL;
        } catch (ParseException e) {
            return ValidationResponse.BIRTHDAY_INVALID;
        }
    }

}
