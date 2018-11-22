package de.digiwill.model;


import de.digiwill.SystemHandle;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;

import java.util.ArrayList;
import java.util.List;

@TypeAlias("EmailAction")
public class EmailAction extends BaseAction {
    private List<String> recipients = new ArrayList<>();
    private String subject;
    private boolean isHTMLContent;
    private String content;

    @PersistenceConstructor
    public EmailAction(ObjectId UID, boolean wasCompleted, String subject, boolean isHTMLContent, String content) {
        this(subject, isHTMLContent, content);
        this.UID = UID;
        this.wasCompleted = wasCompleted;
    }

    public EmailAction(String subject, boolean isHTMLContent, String content) {
        this.subject = subject;
        this.isHTMLContent = isHTMLContent;
        this.content = content;
        this.type = ActionType.EMAIL;
    }

    @Override
    public void execute(SystemHandle systemHandle) {//TODO implement

    }
}
