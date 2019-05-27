package de.digiwill.service.registration;

import org.springframework.util.MultiValueMap;

public class PasswordMatchValidator extends RegistrationValidator {
    public PasswordMatchValidator() {
        super( RegistrationResponse.PASSWORD_MISMATCH);
    }

    @Override
    public boolean validate(final MultiValueMap<String, String> formData) {
        String password = formData.getFirst("password");
        String passwordRep = formData.getFirst("password_rep");
        return password != null && password.equals(passwordRep);
    }
}
