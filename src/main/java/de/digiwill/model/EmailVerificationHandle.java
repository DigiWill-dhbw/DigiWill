package de.digiwill.model;

import java.time.Instant;

public class EmailVerificationHandle extends EmailResponseHandle {

    private final static int VERIFICATION_TIMEOUT = 10;

    public EmailVerificationHandle(UserHandle userHandle) throws IllegalArgumentException{
        super(userHandle, Instant.now().getEpochSecond() + VERIFICATION_TIMEOUT*60);
        if (userHandle.isVerified()) {
            throw new IllegalArgumentException("User already verified");
        }else{
            initialize();
        }
    }

    @Override
    protected void initialize() {

    }

    @Override
    public String getLinkSuffix() { return "confirmEmail?"+getUID().toString(); }
}
