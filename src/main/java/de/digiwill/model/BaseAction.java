package de.digiwill.model;

import de.digiwill.service.EmailDispatcher;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

public abstract class BaseAction {

    @Field("_id")
    protected ObjectId UID;
    protected boolean wasCompleted;
    protected ActionType type;

    public ActionSuccess execute(EmailDispatcher emailDispatcher) {
        if (wasCompleted) {
            return ActionSuccess.SUCCESSFUL_PREVIOUSLY;
        }
        ActionSuccess actionSuccess = executeAction(emailDispatcher);
        wasCompleted = actionSuccess.success;
        return actionSuccess;
    }

    protected abstract ActionSuccess executeAction(EmailDispatcher emailDispatcher);

    public ObjectId getUID() {
        return UID;
    }

    public ActionType getType() {
        return type;
    }

    public boolean wasCompleted() {
        return wasCompleted;
    }

    public enum ActionSuccess {
        SUCCESS(true, "Success"),
        SUCCESSFUL_PREVIOUSLY(true, "Executed earlier"),
        FAILURE(false, "Failure");

        boolean success;
        String text;

        ActionSuccess(boolean success, String text) {
            this.success = success;
            this.text = text;
        }

        public boolean wasSuccessful() {
            return success;
        }
    }
}
