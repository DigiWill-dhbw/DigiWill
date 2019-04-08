package de.digiwill.service.registration;

import de.digiwill.util.RegexMatcher;
import org.springframework.util.MultiValueMap;

public class PasswordRequirementValidator extends RegistrationValidator {

    public PasswordRequirementValidator() {
        super(RegistrationResponse.PASSWORD_REQUIREMENTS_NOT_MET);
    }

    @Override
    public boolean validate(final MultiValueMap<String, String> formData) {
        String password = formData.getFirst("password");
        return password != null && RegexMatcher.isValidPassword(password);
    }
}
