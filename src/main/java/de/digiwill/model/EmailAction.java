package de.digiwill.model;

import de.digiwill.exception.EmailException;
import de.digiwill.service.EmailDispatcher;
import de.digiwill.util.RegexMatcher;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@TypeAlias("EmailAction")
public class EmailAction extends BaseAction {

    @Autowired
    private EmailDispatcher emailDispatcher;

    @Transient
    private Logger logger = LoggerFactory.getLogger(EmailAction.class);

    private List<String> recipients = new ArrayList<>();
    private String subject;
    private boolean isHTMLContent;
    private String content;

    @PersistenceConstructor
    public EmailAction(ObjectId UID, boolean wasCompleted, List<String> recipients, String subject, boolean isHTMLContent, String content) {
        this(recipients, subject, isHTMLContent, content);
        this.UID = UID;
        this.wasCompleted = wasCompleted;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isHTMLContent() {
        return isHTMLContent;
    }

    public void setHTMLContent(boolean HTMLContent) {
        isHTMLContent = HTMLContent;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecipientString() {
        String str = "";
        for (String s :
                this.recipients) {
            str += s + " ";
        }
        return str;
    }

    public String getSkipedContent() {
        if (this.content.length() > 20) {
            return this.content.substring(0, 20) + "...";
        } else {
            return this.content;
        }

    }

    public EmailAction(List<String> recipients, String subject, boolean isHTMLContent, String content) {
        this.recipients = recipients;
        this.subject = subject;
        this.isHTMLContent = isHTMLContent;
        this.content = content;
        this.type = ActionType.EMAIL;
    }

    public static EmailAction generateEmailAction(@RequestParam(name = "recipients", required = true) String recipients, @RequestParam(name = "subject", required = true) String subject, @RequestParam(name = "content", required = true) String content) throws EmailException {
        List<String> recipient_list = new ArrayList<>();
        String[] recipient_array = recipients.split(" ");
        for (String recipient : recipient_array) {
            if (RegexMatcher.isValidEmailAddress(recipient)) {
                recipient_list.add(recipient);
            } else {
                throw new EmailException("Recipient List contains invalid Email Address");
            }

        }
        return new EmailAction(recipient_list, subject, false, content);
    }

    @Override
    public ActionSuccess executeAction() {
        try {
            emailDispatcher.sendEmail(recipients, subject, isHTMLContent, content);
        } catch (EmailException e) {
            logger.error(e.getMessage());
            return ActionSuccess.FAILURE;
        }
        return ActionSuccess.SUCCESS;
    }
}
