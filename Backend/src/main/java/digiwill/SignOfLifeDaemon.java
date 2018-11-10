package digiwill;

import java.util.List;

public class SignOfLifeDaemon {
    private SystemHandle systemHandle;

    public SignOfLifeDaemon(SystemHandle systemHandle) {
        this.systemHandle = systemHandle;
    }

    public void start() {
    }

    public void check(List<User> users) {
    }

    public SystemHandle getSystemHandle() {
        return systemHandle;
    }
}
