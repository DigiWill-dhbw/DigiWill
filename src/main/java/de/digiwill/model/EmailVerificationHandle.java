package de.digiwill.model;

import de.digiwill.repository.EmailResponseHandleRepository;
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
        //TODO what? what should be done while initilazing
    }

    public void executeCallback(UserHandleManager userHandleManager, EmailResponseHandleRepository emailResponseHandleRepository){
        UserHandle userHandle = userHandleManager.loadUserByEmailAddress(this.getEmailAddress());
        userHandle.setVerified(true);
        userHandleManager.updateUser(userHandle);
        emailResponseHandleRepository.delete(this);
    }

    @Override
    public void executeTimeout() {
        //TODO delete user or set user to inactive?
    }

}
