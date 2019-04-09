package de.digiwill.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "responseHandles")
public abstract class EmailResponseHandle {

    @Id
    @Field("_id")
    private ObjectId UID;
    private String emailAddress;
    private long timeout;

    EmailResponseHandle(UserHandle userHandle, long timeout) {
        this.emailAddress = userHandle.getEmailAddress();
        this.timeout = timeout;
    }

    protected abstract void initialize();

    public abstract String getLinkSuffix();

    public ObjectId getUID(){
        return this.UID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public long getTimeout() {
        return timeout;
    }
}
