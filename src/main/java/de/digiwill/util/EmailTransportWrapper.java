package de.digiwill.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

public class EmailTransportWrapper {

    public void sendMessage(Message m) throws MessagingException {
        Transport.send(m);
    }


}
