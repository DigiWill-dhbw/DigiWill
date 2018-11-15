package digiwill;

public enum ActionType {

    EMAIL(0);

    private int type;

    ActionType(int type) {
        this.type = type;
    }

    public int getActionType() {
        return type;
    }

}
