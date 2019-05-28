package de.digiwill.model;

import de.digiwill.service.EmailDispatcher;

import java.util.ArrayList;
import java.util.List;

public class ActionSet {
    private  List<BaseAction> actions;
    private boolean allActionsCompleted;

    public ActionSet(List<BaseAction> actions, boolean allActionsCompleted) {
        this.actions = actions;
        this.allActionsCompleted = allActionsCompleted;
    }

    public static ActionSet getInitial(){
        return new ActionSet(new ArrayList<>(), false);
    }

    public List<BaseAction> getActions() {
        return actions;
    }

    public boolean areAllActionsCompleted() {
        return allActionsCompleted;
    }

    public void setAllActionsCompleted() {
        this.allActionsCompleted = true;
    }

    public void remove(int index) {
        actions.remove(index);
    }

    public void replace(int index, BaseAction action) {
        actions.set(index, action);
    }

    public void add(BaseAction action) {
        actions.add(action);
    }

    public void executeActions(EmailDispatcher emailDispatcher) {
        boolean allCompleted = true;
        for (BaseAction action : actions) {
            if (!action.wasCompleted()) {
                allCompleted = action.execute(emailDispatcher).wasSuccessful() && allCompleted;
            }
        }
        if(allCompleted) {
            setAllActionsCompleted();
        }
    }
}
