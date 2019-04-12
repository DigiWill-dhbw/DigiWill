package de.digiwill.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.Instant;

public class EmailVerificationHandle extends EmailResponseHandle {

    private final static int VERIFICATION_TIMEOUT = 10;

    @PersistenceConstructor
    public EmailVerificationHandle(ObjectId UID, String emailAddress, String token, long timeout){
        super(UID, emailAddress, token, timeout);
    }

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
        //TODO
    }

    @Override
    public void executeCallback(){
        //TODO set user to registered
    }

    @Override
    public void executeTimeout() {
        //TODO delete user or set user to inactive?
    }

}
