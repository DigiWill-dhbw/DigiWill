package de.digiwill.util;

import de.digiwill.model.UserHandle;

public class EmailVerificationHandle extends EmailResponseHandle {

    public EmailVerificationHandle(UserHandle userHandle) throws Exception{
        super(userHandle);
        if (userHandle.isVerified()) {
            throw new Exception("User already verified");
        }else{
            initialize();
        }
    }

    @Override
    protected void initialize() {

    }

    @Override
    public EmailResponseHandle getLinkSuffix() {
        return null;
    }
}
