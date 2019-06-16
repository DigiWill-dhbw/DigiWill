package de.digiwill.model;

import org.junit.Assert;
import org.junit.Test;

public class ActionTypeTest {

    @Test
    public void getActionType() {
        ActionType type = ActionType.EMAIL;
        Assert.assertEquals(0, type.getActionType());
    }

}
