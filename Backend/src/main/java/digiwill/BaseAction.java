package digiwill;

public abstract class BaseAction {

    private long UID;
    public static final int EMAIL_ACTION = 0;
    private boolean wasCompleted = false;
    private int type;

    void execute(SystemHandle systemHandle){

    }

    public long getUID() {
        return UID;
    }

    public int getType() {
        return type;
    }
}
