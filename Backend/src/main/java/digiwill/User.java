package digiwill;

import java.util.ArrayList;
import java.util.List;

public class User {

    private long UID;
    /**
     * UTC timestamp
     */
    private long lastSignOfLife;
    private long lastReminder;
    private long deltaReminder;
    private long deltaDeathTime;
    private boolean isDead;
    private boolean isVerified;
    private PersonalData personalData;

    private List<BaseAction> actions = new ArrayList<>();
    private String emailAddress;
    private String alias;

    public boolean isValidNewUser(){
        return false; //TODO implement
    }

    public long getUID() {
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
}
