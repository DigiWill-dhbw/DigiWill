package de.digiwill.model;

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

    }

    @Override
    public String getLinkSuffix() { return "confirmEmail?"+getUID().toString(); }
}
