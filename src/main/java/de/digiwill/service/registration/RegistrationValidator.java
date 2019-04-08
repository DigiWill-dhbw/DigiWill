package de.digiwill.service.registration;

import org.springframework.util.MultiValueMap;

public class RegistrationValidator {
    private RegistrationResponse response;

    public RegistrationValidator(RegistrationResponse response){
        this.response = response;
    }

    public boolean validate(final MultiValueMap<String, String> formData){
        return false;
    }

    public RegistrationResponse getResponse(){
        return response;
    }

    public void setResponse(RegistrationResponse response) {
        this.response = response;
    }
}
