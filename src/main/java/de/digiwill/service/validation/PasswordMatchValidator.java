package de.digiwill.service.validation;

import org.springframework.util.MultiValueMap;

public class PasswordMatchValidator extends Validator {
    public PasswordMatchValidator() {
        super( ValidationResponse.PASSWORD_MISMATCH);
    }

    @Override
    public boolean validate(final MultiValueMap<String, String> formData) {
        String password = formData.getFirst("password");
        String passwordRep = formData.getFirst("password_rep");
        return password != null && password.equals(passwordRep);
    }
}
