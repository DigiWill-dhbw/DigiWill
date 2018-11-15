package digiwill;

public class EmailResponseHandle {
    public static final int REGISTER_HANDLE = 0;
    public static final int RESET_HANDLE = 1;

    private UserHandle userHandle;
    private String handle;
    private int type;

    private EmailResponseHandle(UserHandle userHandle, String handle, int type) {
        this.userHandle = userHandle;
        this.handle = handle;
        this.type = type;
    }

    public static EmailResponseHandle getRegistrationHandle(UserHandle userHandle) { //TODO implement
        if (userHandle.isVerified()) {
            return null;
        }
        return null;
    }

    public static EmailResponseHandle getResetHandle(UserHandle userHandle) { //TODO implement
        return null;
    }

    public UserHandle getUserHandle() {
        return userHandle;
    }
}
