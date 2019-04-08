package de.digiwill.util;

import de.digiwill.model.UserHandle;

public class EmailVerificationHandle extends EmailResponseHandle {

    public EmailVerificationHandle(UserHandle userHandle) throws IllegalArgumentException{
        super(userHandle);
        if (userHandle.isVerified()) {
            throw new IllegalArgumentException("User already verified");
        }else{
            initialize();
        }
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
