package de.digiwill.model;

import de.digiwill.SystemHandle;
import de.digiwill.exception.EmailException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.TypeAlias;
import java.util.ArrayList;
import java.util.List;

@TypeAlias("EmailAction")
public class EmailAction extends BaseAction {

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

    @Override
    public int execute(SystemHandle systemHandle) {
        try {
           systemHandle.getEmailDispatcher().sendEmail(recipients, subject, isHTMLContent, content);
        } catch (EmailException e) {
            logger.error(e.getMessage());
            return 1;
        }
        return 0;
    }
}
