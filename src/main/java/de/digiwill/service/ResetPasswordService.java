package de.digiwill.service;


import de.digiwill.exception.EmailException;
import de.digiwill.model.EmailResetHandle;
import de.digiwill.model.UserHandle;
import de.digiwill.repository.EmailResponseHandleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class ResetPasswordService {

    @Autowired
    private UserHandleManager userHandleManager;
    @Autowired
    private EmailResponseHandleManager emailResponseHandleManager;
    @Autowired
    private EmailDispatcher emailDispatcher;
    @Autowired
    private EmailResponseHandleRepository emailResponseHandleRepository;

    public void requestPasswordReset(MultiValueMap<String, String> formData){
        EmailResetHandle emailResetHandle = null;
        try {
            UserHandle userHandle = userHandleManager.loadUserByEmailAddress(formData.getFirst("email"));
            emailResetHandle = new EmailResetHandle(userHandle);
            emailResponseHandleRepository.insert(emailResetHandle);

            emailDispatcher.sendResetEmail(emailResetHandle, userHandle);
        } catch (EmailException e) {
            emailResponseHandleManager.deleteEmailResponseHandle(emailResetHandle);
        } catch (Exception e) {
        }
    }
}
