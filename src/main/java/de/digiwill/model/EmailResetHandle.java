package de.digiwill.model;

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
    public void executeTimeout() {
        //TODO implement
    }


}
