package de.digiwill;

import de.digiwill.util.EmailDispatcher;

import java.util.logging.Logger;

public class SystemHandle {
    private EmailDispatcher emailDispatcher;
    private Logger logger;

    public SystemHandle() { //TODO implement
    }

    public EmailDispatcher getEmailDispatcher() {
        return emailDispatcher;
    }

    public Logger getLogger() {
        return logger;
    }
}
