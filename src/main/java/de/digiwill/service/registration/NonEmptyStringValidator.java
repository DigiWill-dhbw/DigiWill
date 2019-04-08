package de.digiwill.service.registration;

import org.springframework.util.MultiValueMap;

public class NonEmptyStringValidator extends RegistrationValidator {
    private String parameterName;
    public NonEmptyStringValidator(String parameterName, RegistrationResponse response) {
        super(response);
        this.parameterName = parameterName;
    }

    @Override
    public boolean validate(final MultiValueMap<String, String> formData) {
        String value = formData.getFirst(parameterName);
        return value != null && value.length() > 0;
    }
}
