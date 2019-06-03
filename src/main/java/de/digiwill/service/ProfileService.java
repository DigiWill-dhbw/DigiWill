package de.digiwill.service;

import de.digiwill.model.UserHandle;
import de.digiwill.service.UserHandleManager;
import de.digiwill.service.validation.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class ProfileService {

    @Autowired
    private UserHandleManager userHandleManager;

    public ProfileService() {

    }

    public UserHandle getUserHandleByEmail(String email) {
        return userHandleManager.loadUserByEmailAddress(email);
    }

    public ValidationResponse editUser(final MultiValueMap<String, String> formData) {
        if (formData == null) {
            return ValidationResponse.FORM_DATA_DOESNT_EXIST;
        }
        return null;
    }

}
