package digiwill;

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
