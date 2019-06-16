package de.digiwill.model;

import org.junit.Assert;
import org.junit.Test;

public class ActionSuccessTest {

    @Test
    public void wasSuccessful() {
        Assert.assertTrue( BaseAction.ActionSuccess.SUCCESS.wasSuccessful());
        Assert.assertTrue(BaseAction.ActionSuccess.SUCCESSFUL_PREVIOUSLY.wasSuccessful());
        Assert.assertFalse(BaseAction.ActionSuccess.FAILURE.wasSuccessful());
    }

}
