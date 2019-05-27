package de.digiwill.service.registration;

import org.springframework.util.MultiValueMap;

public abstract class RegistrationValidator {
    private RegistrationResponse response;

    public RegistrationValidator(RegistrationResponse response){
        this.response = response;
    }

    public abstract boolean validate(final MultiValueMap<String, String> formData);

    public RegistrationResponse getResponse(){
        return response;
    }

    public void setResponse(RegistrationResponse response) {
        this.response = response;
    }
}
