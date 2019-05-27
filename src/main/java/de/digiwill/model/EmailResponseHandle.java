package de.digiwill.model;

import de.digiwill.service.EmailResponseHandleManager;
import de.digiwill.service.UserHandleManager;
import de.digiwill.service.callback.CallbackResponse;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.security.SecureRandom;

@Document(collection = "responseHandles")
public abstract class EmailResponseHandle {

    @Id
    @Field("_id")
    private ObjectId UID;
    private String emailAddress;
    private String token;
    /*
    * Timestamp after which handle will be invalid
    */
    private long timeout;

    EmailResponseHandle(ObjectId UID, String emailAddress, String token, long timeout){
        this.UID = UID;
        this.emailAddress = emailAddress;
        this.token = token;
        this.timeout = timeout;
    }

    EmailResponseHandle(UserHandle userHandle, long timeout) {
        this.emailAddress = userHandle.getEmailAddress();
        this.token = generateToken(20);
        this.timeout = timeout;
    }

    private String generateToken( int len ){
        final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ ) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    protected abstract void initialize();

    public abstract CallbackResponse executeCallback(UserHandleManager userHandleManager, EmailResponseHandleManager emailResponseHandleManager);

    public abstract void executeTimeout(UserHandleManager userHandleManager, EmailResponseHandleManager emailResponseHandleManager);

    public  String getLinkSuffix(){
        return "callback?id=" + getUID().toString() +"&token=" + token;
    }

    public boolean verifyToken(String compareToken){
        return compareToken.contentEquals(token);
    }

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
