package de.digiwill.service.registration;

import org.springframework.util.MultiValueMap;

public class PasswordMatchValidator extends RegistrationValidator {
    public PasswordMatchValidator() {
        super( RegistrationResponse.PASSWORD_MISMATCH);
    }

    @Override
    public boolean validate(final MultiValueMap<String, String> formData) {
        String password_1 = formData.getFirst("password");
        String password_2 = formData.getFirst("password_rep");
        return password_1 != null && password_1.equals(password_2);
    }
}
