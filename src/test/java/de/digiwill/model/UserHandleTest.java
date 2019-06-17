package de.digiwill.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UserHandleTest {

    @Test
    public void getAndSetDeltaReminder() {
        UserHandle userHandle = UserHandleFactory.createUserHandleWithEmailPasswordAuthorities("testEmail", "testPassword", new AuthoritySet(new ArrayList<>()));
        userHandle.setDeltaReminder(1000);
        Assert.assertEquals(1000, userHandle.getDeltaReminder());
    }

    @Test
    public void setDead() {
        UserHandle userHandle = UserHandleFactory.createUserHandleWithEmailPasswordAuthorities("testEmail", "testPassword", new AuthoritySet(new ArrayList<>()));
        userHandle.setDead();
        Assert.assertTrue(userHandle.isDead());
    }

    @Test
    public void getWebhookActionIdx() {
        UserHandle userHandle = UserHandleFactory.createUserHandleWithEmailPasswordAuthorities("testEmail", "testPassword", new AuthoritySet(new ArrayList<>()));
        Assert.assertEquals(-1, userHandle.getWebhookActionIdx());

        userHandle.addAction(new WebhookAction("testURL"));
        Assert.assertEquals(0, userHandle.getWebhookActionIdx());
    }

    @Test
    public void getWebhookAction() {
        UserHandle userHandle = UserHandleFactory.createUserHandleWithEmailPasswordAuthorities("testEmail", "testPassword", new AuthoritySet(new ArrayList<>()));
        WebhookAction webhookAction = new WebhookAction("testURL");
        userHandle.addAction(webhookAction);
        Assert.assertEquals(webhookAction, userHandle.getWebhookAction());
    }

    @Test
    public void getLastInteractionWithUser() {
        UserHandle userHandle = UserHandleFactory.createUserHandleWithEmailPasswordAuthorities("testEmail", "testPassword", new AuthoritySet(new ArrayList<>()));
        Assert.assertEquals(-1, userHandle.getLastInteractionWithUser());
    }

}
