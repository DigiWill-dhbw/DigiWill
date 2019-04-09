package de.digiwill.util;

import de.digiwill.model.UserHandle;

public abstract class EmailResponseHandle {

    private UserHandle userHandle;

    EmailResponseHandle(UserHandle userHandle) {
        this.userHandle = userHandle;
    }

    protected abstract void initialize();

    public abstract EmailResponseHandle getLinkSuffix();


    public UserHandle getUserHandle() {
        return userHandle;
    }
}
