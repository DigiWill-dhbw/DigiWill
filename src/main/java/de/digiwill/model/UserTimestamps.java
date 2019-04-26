package de.digiwill.model;

import java.time.Instant;

public class UserTimestamps {
    /**
     * Unix timestamp in seconds
     */
    private long lastSignOfLife;
    /**
     * Unix timestamp in seconds
     */
    private long lastReminder;

    public UserTimestamps(long lastSignOfLife, long lastReminder) {
        this.lastSignOfLife = lastSignOfLife;
        this.lastReminder = lastReminder;
    }

    public static UserTimestamps getInitial(){
        return new UserTimestamps(-1, -1);
    }

    public long getLastInteraction(){
        return Math.max(lastSignOfLife, lastReminder);
    }

    public long getLastSignOfLife() {
        return lastSignOfLife;
    }

    public long getLastReminder() {
        return lastReminder;
    }

    public void setLastReminder(long lastReminder) {
        this.lastReminder = lastReminder;
    }

    public void sendLifeSign(){
        this.lastSignOfLife = Instant.now().getEpochSecond();
    }
}
