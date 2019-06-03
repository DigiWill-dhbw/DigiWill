package de.digiwill.service;

import de.digiwill.model.UserHandle;
import de.digiwill.service.UserHandleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private UserHandleManager userHandleManager;

    public ProfileService() {

    }

    public UserHandle getUserHandleByEmail(String email) {
        return userHandleManager.loadUserByEmailAddress(email);
    }



}
