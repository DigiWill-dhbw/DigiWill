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

    EmailResponseHandle(UserHandle userHandle) {
        this.emailAddress = userHandle.getEmailAddress();
    }

    protected abstract void initialize();

    public abstract String getLinkSuffix();

    public ObjectId getUID(){
        return this.UID;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
