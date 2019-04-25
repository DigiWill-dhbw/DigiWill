package de.digiwill.model;

import de.digiwill.service.EmailResponseHandleManager;
import de.digiwill.service.UserHandleManager;
import de.digiwill.service.callback.CallbackResponse;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;

public class EmailResetHandle extends EmailResponseHandle {

    @PersistenceConstructor
    public EmailResetHandle(ObjectId UID, String emailAddress, String token, long timeout){
        super(UID, emailAddress, token, timeout);
    }

    public EmailResetHandle(UserHandle userHandle) {
        super(userHandle, -1);
        initialize();
    }

    @Override
    protected void initialize() {
        //TODO implement
    }

    @Override
    public CallbackResponse executeCallback(UserHandleManager userHandleManager, EmailResponseHandleManager emailResponseHandleManager){
        return CallbackResponse.CALLBACK_RESET_SUCCESS;
    }

    @Override
    public void executeTimeout(UserHandleManager userHandleManager, EmailResponseHandleManager emailResponseHandleManager) {
        //TODO implement
    }


}
