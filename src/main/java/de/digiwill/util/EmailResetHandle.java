package de.digiwill.util;

import de.digiwill.model.UserHandle;

public class EmailResetHandle extends EmailResponseHandle {

    public EmailResetHandle(UserHandle userHandle) {
        super(userHandle);
        initialize();
    }

    @Override
    protected void initialize() {
        //TODO implement
    }

    @Override
    public EmailResponseHandle getLinkSuffix() {
        return null;
    }
}
