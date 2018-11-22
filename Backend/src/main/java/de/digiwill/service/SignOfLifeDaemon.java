package de.digiwill.service;

import de.digiwill.SystemHandle;
import de.digiwill.model.UserHandle;

import java.util.List;

public class SignOfLifeDaemon {
    private SystemHandle systemHandle;

    public SignOfLifeDaemon(SystemHandle systemHandle) {
        this.systemHandle = systemHandle;
    }

    public void start() { //TODO implement
    }

    public void check(List<UserHandle> userHandles) { //TODO implement
    }

    public SystemHandle getSystemHandle() {
        return systemHandle;
    }
}
