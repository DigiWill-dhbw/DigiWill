package de.digiwill.service.registration;

import org.springframework.util.MultiValueMap;

public class PasswordRequirementValidator extends RegistrationValidator {

    private static String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=ß!\"§~'`<>|;:,.\\-\\\\\\/\\?])(?=\\S+$).{8,}$";

    public PasswordRequirementValidator() {
        super(RegistrationResponse.PASSWORD_REQUIREMENTS_NOT_MET);
    }

    @Override
    public boolean validate(final MultiValueMap<String, String> formData) {
        String password = formData.getFirst("password");
        return password != null && password.matches(passwordRegex);
    }
}
