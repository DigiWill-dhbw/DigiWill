package de.digiwill;

import de.digiwill.util.EmailDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SystemHandle {
    @Autowired
    private EmailDispatcher emailDispatcher;

    public SystemHandle() {
    }

    public EmailDispatcher getEmailDispatcher() {
        return emailDispatcher;
    }
}
