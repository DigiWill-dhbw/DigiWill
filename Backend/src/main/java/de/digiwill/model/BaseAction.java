package de.digiwill.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

public abstract class BaseAction {

    @Field("_id")
    protected ObjectId UID;
    protected boolean wasCompleted;
    protected ActionType type;

    public ActionSuccess execute(){
        if(wasCompleted){
            return  ActionSuccess.SUCCESSFUL_PREVIOUSLY;
        }
        return executeAction();
    }

    protected abstract ActionSuccess executeAction();

    public ObjectId getUID() {
        return UID;
    }

    public ActionType getType() {
        return type;
    }

    public boolean wasCompleted() {
        return wasCompleted;
    }

    public enum ActionSuccess{
        SUCCESS (true, "Success"),
        SUCCESSFUL_PREVIOUSLY(true, "Executed earlier"),
        FAILURE(false, "Failure");

        boolean success;
        String text;
        ActionSuccess( boolean success, String text){
            this.success = success;
            this.text = text;
        }

        public boolean wasSuccessful() {
            return success;
        }
    }
}
