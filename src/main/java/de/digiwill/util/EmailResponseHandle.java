package de.digiwill.util;

import de.digiwill.model.UserHandle;

public class EmailResponseHandle {

    private UserHandle userHandle;
    private String handle;
    private EmailResponseType type;

    private EmailResponseHandle(UserHandle userHandle, String handle, EmailResponseType type) {
        this.userHandle = userHandle;
        this.handle = handle;
        this.type = type;
    }

    public static EmailResponseHandle getRegistrationHandle(UserHandle userHandle) { //TODO implement
        if (userHandle.isVerified()) {
            return null;
        }
        return new EmailResponseHandle(userHandle, "", EmailResponseType.REGISTER);
    }

    public static EmailResponseHandle getResetHandle(UserHandle userHandle) { //TODO implement
        return null;
    }

    public UserHandle getUserHandle() {
        return userHandle;
    }
}
