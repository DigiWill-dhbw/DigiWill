package de.digiwill.model;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BaseActionTest {

    @Test
    public void getUID() {
        ObjectId testId = new ObjectId();
        List<String> recipients = new ArrayList<>();
        BaseAction action = new EmailAction(testId, false, recipients, "Test", false, "Test");
        Assert.assertEquals(testId, action.getUID());
    }

    @Test
    public void getType() {
        ObjectId testId = new ObjectId();
        List<String> recipients = new ArrayList<>();
        BaseAction action = new EmailAction(testId, false, recipients, "Test", false, "Test");
        Assert.assertEquals(ActionType.EMAIL, action.getType());
    }

    @Test
    public void wasCompleted() {
        ObjectId testId = new ObjectId();
        List<String> recipients = new ArrayList<>();
        BaseAction action = new EmailAction(testId, false, recipients, "Test", false, "Test");
        Assert.assertFalse(action.wasCompleted());
    }

}
