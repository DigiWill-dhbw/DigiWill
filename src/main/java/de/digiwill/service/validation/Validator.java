package de.digiwill.service.validation;

import org.springframework.util.MultiValueMap;

public abstract class Validator {
    private ValidationResponse response;

    public Validator(ValidationResponse response){
        this.response = response;
    }

    public abstract boolean validate(final MultiValueMap<String, String> formData);

    public ValidationResponse getResponse(){
        return response;
    }

    public void setResponse(ValidationResponse response) {
        this.response = response;
    }
}
