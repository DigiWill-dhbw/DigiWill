package digiwill;

import java.util.List;

public class EmailDispatcher {
    public void sendRegistrationConfirmationEmail(EmailResponseHandle responseHandle) throws EmailException {

        try {
            sendEmail(null, null, true, null);
        } catch (EmailException e) {
            throw new EmailException("Failed to send registration Email", e);
        }
    }

    public void sendEmail(List<String> recipients, String subject, boolean htmlContentFlag, String content) throws EmailException {

    }

    public void sendResetEmail(EmailResponseHandle responseHandle) throws EmailException {

        try {
            sendEmail(null, null, true, null);
        } catch (EmailException e) {
            throw new EmailException("Failed to send reset Email", e);
        }
    }

    public void sendReminderEmail(User user) throws EmailException {

        try {
            sendEmail(null, null, true, null);
        } catch (EmailException e) {
            throw new EmailException("Failed to send reminder", e);
        }
    }


}
