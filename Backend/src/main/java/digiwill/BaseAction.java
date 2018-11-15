package digiwill;

import org.bson.types.ObjectId;

public abstract class BaseAction {

    @Field("_id")
    protected ObjectId UID;
    protected boolean wasCompleted;
    protected ActionType type;

    public abstract void execute(SystemHandle systemHandle);

    public ObjectId getUID() {
        return UID;
    }

    public ActionType getType() {
        return type;
    }
}
