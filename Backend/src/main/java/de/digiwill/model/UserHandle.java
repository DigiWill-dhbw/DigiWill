package de.digiwill.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "users")
@TypeAlias("user")
public class UserHandle {

    @Id
    @Field("_id")
    private ObjectId UID;

    private String emailAddress;
    private String alias;
    private PersonalData personalData;

    /**
     * UTC timestamp
     */
    private long lastSignOfLife;
    private long lastReminder;
    private long deltaReminder;
    private long deltaDeathTime;
    private boolean isDead;
    private boolean isVerified;

    private Iterable<BaseAction> actions;

    @PersistenceConstructor
    public UserHandle(ObjectId UID, String emailAddress, PersonalData personalData, String alias, long lastSignOfLife, long lastReminder, long deltaReminder, long deltaDeathTime, boolean isDead, boolean isVerified, Iterable<BaseAction> actions) {
        this(emailAddress, personalData, alias, lastSignOfLife, lastReminder, deltaReminder, deltaDeathTime, isDead, isVerified, actions);
        this.UID = UID;
    }

    public UserHandle(String emailAddress, PersonalData personalData, String alias, long lastSignOfLife, long lastReminder, long deltaReminder, long deltaDeathTime, boolean isDead, boolean isVerified, Iterable<BaseAction> actions) {
        this.emailAddress = emailAddress;
        this.alias = alias;
        this.personalData = personalData;
        this.lastSignOfLife = lastSignOfLife;
        this.lastReminder = lastReminder;
        this.deltaReminder = deltaReminder;
        this.deltaDeathTime = deltaDeathTime;
        this.isDead = isDead();
        this.isVerified = isVerified;
        this.actions = actions;
    }


    public boolean isValidNewUser() {
        return false; //TODO implement
    }

    public ObjectId getUID() {
        return UID;
    }

    public long getLastSignOfLife() {
        return lastSignOfLife;
    }

    public void setLastSignOfLife(long lastSignOfLife) {
        this.lastSignOfLife = lastSignOfLife;
    }

    public long getLastReminder() {
        return lastReminder;
    }

    public void setLastReminder(long lastReminder) {
        this.lastReminder = lastReminder;
    }

    public long getDeltaReminder() {
        return deltaReminder;
    }

    public void setDeltaReminder(long deltaReminder) {
        this.deltaReminder = deltaReminder;
    }

    public long getDeltaDeathTime() {
        return deltaDeathTime;
    }

    public void setDeltaDeathTime(long deltaDeathTime) {
        this.deltaDeathTime = deltaDeathTime;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAlias() {
        return alias;
    }

    public Iterable<BaseAction> getActions() {
        return actions;
    }
}
