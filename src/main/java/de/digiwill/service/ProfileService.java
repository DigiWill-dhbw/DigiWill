package de.digiwill.service;

import de.digiwill.model.Address;
import de.digiwill.model.PersonalData;
import de.digiwill.model.UserHandle;
import de.digiwill.service.validation.*;
import de.digiwill.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    private UserHandleManager userHandleManager;

    private List<Validator> editProfileValidators;
    private List<Validator> changePasswordValidators;

    public ProfileService() {
        editProfileValidators = new ArrayList<>();
        editProfileValidators.add(new NonEmptyStringValidator("firstNameInput", ValidationResponse.NO_FIRST_NAME));
        editProfileValidators.add(new NonEmptyStringValidator("surNameInput", ValidationResponse.NO_SURNAME));
        editProfileValidators.add(new BirthdayValidator());

        changePasswordValidators = new ArrayList<>();
        changePasswordValidators.add(new PasswordRequirementValidator());
        changePasswordValidators.add(new PasswordMatchValidator());
    }

    public ValidationResponse changePassword(final MultiValueMap<String, String> formData, final String email) {
        if (formData == null) {
            return ValidationResponse.FORM_DATA_DOESNT_EXIST;
        }

        for (Validator validator : changePasswordValidators) {
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

        if (SecurityHelper.getEncoder().matches(formData.getFirst("oldPassword"), existingUser.getPassword())) {
            existingUser.setPassword(formData.getFirst("password"));
            userHandleManager.updateUser(existingUser);
        } else {
            return ValidationResponse.INVALID_PASSWORD;
        }

        return ValidationResponse.SUCCESSFUL;
    }

    public ValidationResponse editUser(final MultiValueMap<String, String> formData, final String email) {
        if (formData == null) {
            return ValidationResponse.FORM_DATA_DOESNT_EXIST;
        }

        for (Validator validator : editProfileValidators) {
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

    public void configureProfilePageModel(Principal principal, Model model, boolean edit) {
        String emailAddress = principal.getName();
        UserHandle user = userHandleManager.loadUserByEmailAddress(emailAddress);
        SimpleDateFormat dateFormat = new SimpleDateFormat(edit ? "yyyy-MM-dd" : "dd.MM.yyyy");
        model.addAttribute("email", emailAddress);
        model.addAttribute("dateOfBirth", dateFormat.format(user.getPersonalData().getDateOfBirth()));
        model.addAttribute("personalData", user.getPersonalData());
        if (!model.containsAttribute("edit")) {
            model.addAttribute("edit", edit);
        }
    }

}
