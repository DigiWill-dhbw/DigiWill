package de.digiwill.model;

import java.util.ArrayList;
import java.util.List;

public class UserActionSet {
    private  List<BaseAction> actions;
    private boolean allActionsCompleted;

    public UserActionSet(List<BaseAction> actions, boolean allActionsCompleted) {
        this.actions = actions;
        this.allActionsCompleted = allActionsCompleted;
    }

    public static UserActionSet getInitial(){
        return new UserActionSet(new ArrayList<>(), false);
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
}
