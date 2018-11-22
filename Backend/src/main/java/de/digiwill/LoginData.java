package de.digiwill;

import de.digiwill.model.UserHandle;

public class LoginData {
    private String username;
    private String passwordHash;

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserHandle getRelatedUser(){
        //TODO implement
        return null;
    }
}
