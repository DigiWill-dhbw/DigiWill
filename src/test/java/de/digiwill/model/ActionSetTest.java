package de.digiwill.model;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ActionSetTest {

    @Test
    public void getEmailActions() {
        ActionSet actionSet = ActionSet.getInitial();
        EmailAction emailAction1 = new EmailAction(Collections.singletonList("example@mail.com"), "THIS IS A SUBJECT", false, "Oh hi, content content");
        EmailAction emailAction2 = new EmailAction(Collections.singletonList("example1@mail.com"), "SUBJECT", false, "This is content");
        WebhookAction webhookAction = new WebhookAction("url.com");
        actionSet.add(emailAction1);
        actionSet.add(webhookAction);
        actionSet.add(emailAction2);

        List<EmailAction> emailActionList = actionSet.getEmailActions();
        assertEquals(2, emailActionList.size());
        assertEquals(emailAction1, emailActionList.get(0));
        assertEquals(emailAction2, emailActionList.get(1));
    }

    @Test
    public void getEmailActionsIdx() {
        ActionSet actionSet = ActionSet.getInitial();
        EmailAction emailAction1 = new EmailAction(Collections.singletonList("example@mail.com"), "THIS IS A SUBJECT", false, "Oh hi, content content");
        EmailAction emailAction2 = new EmailAction(Collections.singletonList("example1@mail.com"), "SUBJECT", false, "This is content");
        WebhookAction webhookAction = new WebhookAction("url.com");
        actionSet.add(emailAction1);
        actionSet.add(webhookAction);
        actionSet.add(emailAction2);

        List<Integer> emailActionIdxList = actionSet.getEmailActionsIdx();
        assertEquals(2, emailActionIdxList.size());
        assertEquals(0, (int)emailActionIdxList.get(0));
        assertEquals(2, (int)emailActionIdxList.get(1));
    }

    @Test
    public void getInitial() {
        ActionSet actionSet = ActionSet.getInitial();
        assertFalse(actionSet.areAllActionsCompleted());
        assertEquals(0, actionSet.getActions().size());
    }

    @Test
    public void setAllActionsCompleted() {
        ActionSet actionSet = ActionSet.getInitial();
        actionSet.setAllActionsCompleted();
        assertTrue(actionSet.areAllActionsCompleted());
    }

    @Test
    public void remove() {
        ActionSet actionSet = ActionSet.getInitial();
        EmailAction emailAction = new EmailAction(Collections.singletonList("example@mail.com"), "THIS IS A SUBJECT", false, "Oh hi, content content");
        actionSet.add(emailAction);
        assertEquals(1, actionSet.getActions().size());
        actionSet.remove(0);
        assertEquals(0, actionSet.getActions().size());
    }

    @Test
    public void replace() {
        ActionSet actionSet = ActionSet.getInitial();
        EmailAction emailAction1 = new EmailAction(Collections.singletonList("example@mail.com"), "THIS IS A SUBJECT", false, "Oh hi, content content");
        EmailAction emailAction2 = new EmailAction(Collections.singletonList("example1@mail.com"), "SUBJECT", false, "This is content");
        actionSet.add(emailAction1);
        actionSet.replace(0, emailAction2);
        assertEquals(1, actionSet.getActions().size());
        assertEquals(emailAction2, actionSet.getActions().get(0));
    }

    @Test
    public void add() {
        ActionSet actionSet = ActionSet.getInitial();
        EmailAction emailAction = new EmailAction(Collections.singletonList("example@mail.com"), "THIS IS A SUBJECT", false, "Oh hi, content content");
        actionSet.add(emailAction);
        assertEquals(1, actionSet.getActions().size());
        assertEquals(emailAction, actionSet.getActions().get(0));
    }
}