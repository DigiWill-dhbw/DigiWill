package de.digiwill.model;

import de.digiwill.service.EmailResponseHandleManager;
import de.digiwill.service.UserHandleManager;
import de.digiwill.service.callback.CallbackResponse;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.Instant;

public class EmailResetHandle extends EmailResponseHandle {

    // Time to timeout in minutes
    private final static int PASSWORD_RESET_TIMEOUT = 10;

    @PersistenceConstructor
    public EmailResetHandle(ObjectId UID, String emailAddress, String token, long timeout){
        super(UID, emailAddress, token, timeout);
    }

    public EmailResetHandle(UserHandle userHandle) {
        super(userHandle, Instant.now().getEpochSecond() + PASSWORD_RESET_TIMEOUT*60);
        initialize();
    }

    @Override
    protected void initialize() {
        //TODO what should happen when it is initialized?
    }

    @Override
    public CallbackResponse executeCallback(UserHandleManager userHandleManager, EmailResponseHandleManager emailResponseHandleManager){
        UserHandle userHandle = userHandleManager.loadUserByEmailAddress(this.getEmailAddress());
        if (userHandle != null) {
            return CallbackResponse.CALLBACK_RESET_PASSWORD;
        } else {
            return CallbackResponse.CALLBACK_ERROR;
        }
    }

    @Override
    public void executeTimeout(UserHandleManager userHandleManager, EmailResponseHandleManager emailResponseHandleManager) {
        emailResponseHandleManager.deleteEmailResponseHandle(this);
    }


}
