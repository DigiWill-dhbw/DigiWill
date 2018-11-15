package digiwill;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

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
