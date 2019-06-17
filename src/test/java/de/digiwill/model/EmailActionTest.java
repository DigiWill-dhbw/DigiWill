package de.digiwill.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmailActionTest {

    @Test
    public void getRecipients() {
        List<String> recipients = Arrays.asList("Recipient 1", "Recipient 2");
        EmailAction action = new EmailAction(recipients, "Test", false, "Test");
        Assert.assertEquals(recipients, action.getRecipients());
    }

    @Test
    public void setRecipients() {
        List<String> recipients = Arrays.asList("Recipient 1", "Recipient 2");
        EmailAction action = new EmailAction(new ArrayList<>(), "Test", false, "Test");
        action.setRecipients(recipients);
        Assert.assertEquals(recipients, action.getRecipients());
    }

    @Test
    public void setSubject() {
        EmailAction action = new EmailAction(new ArrayList<>(), "Test", false, "Test");
        action.setSubject("Test-Subject");
        Assert.assertEquals("Test-Subject", action.getSubject());
    }

    @Test
    public void isHTMLContent() {
        EmailAction action = new EmailAction(new ArrayList<>(), "Test", false, "Test");
        Assert.assertFalse(action.isHTMLContent());
    }

    @Test
    public void setContent() {
        EmailAction action = new EmailAction(new ArrayList<>(), "Test", false, "Test");
        action.setContent("Test-Content");
        Assert.assertEquals("Test-Content", action.getContent());
    }

}
