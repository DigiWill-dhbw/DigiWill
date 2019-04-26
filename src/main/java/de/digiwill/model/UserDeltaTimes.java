package de.digiwill.model;

public class UserDeltaTimes {
    /**
     * Time interval in seconds
     */
    private long deltaReminder;
    /**
     * Time interval in seconds
     */
    private long deltaDeathTime;

    public UserDeltaTimes(long deltaReminder, long deltaDeathTime) {
        this.deltaReminder = deltaReminder;
        this.deltaDeathTime = deltaDeathTime;
    }

    public static UserDeltaTimes getInitial(){
        return new UserDeltaTimes(-1, -1);
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
}
