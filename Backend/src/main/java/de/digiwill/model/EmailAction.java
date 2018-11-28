package de.digiwill.model;

import de.digiwill.SystemHandle;
import de.digiwill.exception.EmailException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;
import java.util.ArrayList;
import java.util.List;

@TypeAlias("EmailAction")
public class EmailAction extends BaseAction {

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
