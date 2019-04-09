package de.digiwill.model;

public class EmailResetHandle extends EmailResponseHandle {

    public EmailResetHandle(UserHandle userHandle) {
        super(userHandle, -1);
        initialize();
    }

    @Override
    protected void initialize() {
        //TODO implement
    }

    @Override
    public String getLinkSuffix() {
        return null;
    }

}
