package de.digiwill.service.registration;

import de.digiwill.util.RegexMatcher;
import org.springframework.util.MultiValueMap;

public class EmailAddressValidator extends RegistrationValidator {

    public EmailAddressValidator() {
        super(RegistrationResponse.INVALID_EMAIL_ADDRESS);
    }

    @Override
    public boolean validate(final MultiValueMap<String, String> formData) {
        String email = formData.getFirst("email");
        return email != null && RegexMatcher.isValidEmailAddress(email);
    }
}
