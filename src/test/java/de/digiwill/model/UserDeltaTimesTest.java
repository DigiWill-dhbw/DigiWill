package de.digiwill.model;

import org.junit.Assert;
import org.junit.Test;

public class UserDeltaTimesTest {

    @Test
    public void getDeltaReminder() {
        UserDeltaTimes times = new UserDeltaTimes(5, 10);
        Assert.assertEquals(5, times.getDeltaReminder());
    }

    @Test
    public void setDeltaReminder() {
        UserDeltaTimes times = new UserDeltaTimes(5, 10);
        times.setDeltaReminder(10);
        Assert.assertEquals(10, times.getDeltaReminder());
    }

    @Test
    public void getDeltaDeathTime() {
        UserDeltaTimes times = new UserDeltaTimes(5, 10);
        Assert.assertEquals(10, times.getDeltaDeathTime());
    }

}
