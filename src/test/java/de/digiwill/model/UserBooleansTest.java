package de.digiwill.model;

import org.junit.Assert;
import org.junit.Test;

public class UserBooleansTest {

    @Test
    public void setAccountNonLocked() {
        UserBooleans userBooleans = new UserBooleans(false, false, false, false);
        userBooleans.setAccountNonLocked(true);
        Assert.assertTrue(userBooleans.isAccountNonLocked());
    }

}
