package de.digiwill.util;

public enum EmailResponseType {

    REGISTER(0),
    RESET(1);

    private int type;

    EmailResponseType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
