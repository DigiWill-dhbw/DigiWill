package de.digiwill.service.validation;

import de.digiwill.util.RegexMatcher;
import org.springframework.util.MultiValueMap;

public class EmailAddressValidator extends Validator {

    public EmailAddressValidator() {
        super(ValidationResponse.INVALID_EMAIL_ADDRESS);
    }

    @Override
    public boolean validate(final MultiValueMap<String, String> formData) {
        String email = formData.getFirst("email");
        return email != null && RegexMatcher.isValidEmailAddress(email);
    }
}
