package digiwill;

public class EmailResponseHandle {
    public static int REGISTER_HANDLE = 0;
    public static int RESET_HANDLE = 0;

    private User user;
    private String handle;
    private int type;

    private EmailResponseHandle(User user, String handle, int type) {
        this.user = user;
        this.handle = handle;
        this.type = type;
    }

    public static EmailResponseHandle getRegistrationHandle(User user) {
        if (user.isVerified()) {
            return null;
        }
        return null;
    }

    public static EmailResponseHandle getResetHandle(User user) {
        return null;
    }


}
