package digiwill;


import java.util.List;

public class EmailAction extends BaseAction {
    private List<String> recipients;
    private String subject;
    private boolean isHTMLContent;
    private String content;


    public EmailAction(List<String> recipients, String subject, boolean isHTMLContent, String content) {
        //TODO call with ID an get data directly from db?
        this.recipients = recipients;
        this.subject = subject;
        this.isHTMLContent = isHTMLContent;
        this.content = content;
        this.wasCompleted = false;
    }

    @Override
    public void execute(SystemHandle systemHandle) {
        try {
            systemHandle.getEmailDispatcher().sendEmail(recipients, subject, isHTMLContent, content);
            this.wasCompleted = true;
        } catch (EmailException e) {
            //TODO implement
            e.printStackTrace();
        }
    }
}
