package de.digiwill.model;

import de.digiwill.service.EmailResponseHandleManager;
import de.digiwill.service.UserHandleManager;
import de.digiwill.service.callback.CallbackResponse;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.Instant;

public class EmailVerificationHandle extends EmailResponseHandle {

    // Time to timeout in minutes
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

    @Override
    public CallbackResponse executeCallback(UserHandleManager userHandleManager, EmailResponseHandleManager emailResponseHandleManager){
        UserHandle userHandle = userHandleManager.loadUserByEmailAddress(this.getEmailAddress());
        userHandle.setVerified(true);
        userHandleManager.updateUser(userHandle);
        emailResponseHandleManager.deleteEmailResponseHandle(this);
        return CallbackResponse.CALLBACK_VERIFICATION_SUCCESS;
    }

    @Override
    public void executeTimeout(UserHandleManager userHandleManager, EmailResponseHandleManager emailResponseHandleManager) {
        userHandleManager.deleteUser(this.getEmailAddress());
        emailResponseHandleManager.deleteEmailResponseHandle(this);
    }

}
