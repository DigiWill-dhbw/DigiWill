package digiwill;

public abstract class BaseAction {
    public static int EMAIL_ACTION = 0;
    private boolean wasCompleted = false;
    private int type;

    void execute(SystemHandle systemHandle){

    }

    public int getType() {
        return type;
    }
}
