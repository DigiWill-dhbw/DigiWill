package digiwill;

import java.util.logging.Logger;

public class SystemHandle {
    private EmailDispatcher emailDispatcher;
    private Logger logger;

    public EmailDispatcher getEmailDispatcher() {
        return emailDispatcher;
    }

    public Logger getLogger() {
        return logger;
    }
}
