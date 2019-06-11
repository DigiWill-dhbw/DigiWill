package de.digiwill.service.validation;

import org.springframework.util.MultiValueMap;

public class NonEmptyStringValidator extends Validator {
    private String parameterName;
    public NonEmptyStringValidator(String parameterName, ValidationResponse response) {
        super(response);
        this.parameterName = parameterName;
    }

    @Override
    public boolean validate(final MultiValueMap<String, String> formData) {
        String value = formData.getFirst(parameterName);
        return value != null && value.length() > 0;
    }
}
