package de.digiwill.service;


import de.digiwill.exception.EmailException;
import de.digiwill.model.EmailResetHandle;
import de.digiwill.model.UserHandle;
import de.digiwill.repository.EmailResponseHandleRepository;
import de.digiwill.service.callback.CallbackService;
import de.digiwill.service.registration.PasswordMatchValidator;
import de.digiwill.service.registration.PasswordRequirementValidator;
import de.digiwill.service.registration.RegistrationResponse;
import de.digiwill.service.registration.RegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private CallbackService callbackService;

    private List<RegistrationValidator> validators = new ArrayList<>();

    public ResetPasswordService() {
        validators.add(new PasswordMatchValidator());
        validators.add(new PasswordRequirementValidator());
    }

    public void requestPasswordReset(MultiValueMap<String, String> formData) {
        EmailResetHandle emailResetHandle = null;
        try {
            UserHandle userHandle = userHandleManager.loadUserByEmailAddress(formData.getFirst("email"));
            emailResetHandle = new EmailResetHandle(userHandle);
            emailResponseHandleRepository.insert(emailResetHandle);

            emailDispatcher.sendResetEmail(emailResetHandle, userHandle);
        } catch (UsernameNotFoundException e){
            //do nothing
        }catch (EmailException e) {
            emailResponseHandleManager.deleteEmailResponseHandle(emailResetHandle);
        }
    }

    public RegistrationResponse resetPassword(MultiValueMap<String, String> formData) {

        if (formData == null) {
            return RegistrationResponse.FORM_DATA_DOESNT_EXIST;
        }

        String id = formData.getFirst("id");
        String token = formData.getFirst("token");
        EmailResetHandle emailResetHandle = (EmailResetHandle) callbackService.getEmailResponseHandle(id, token);


        for (RegistrationValidator validator : validators) {
            if (!validator.validate(formData)) {
                return validator.getResponse();
            }
        }

        if(emailResetHandle != null){
            UserHandle userHandle = userHandleManager.loadUserByEmailAddress(emailResetHandle.getEmailAddress());
            userHandle.setPassword(formData.getFirst("password"));
            userHandleManager.updateUser(userHandle);
            emailResponseHandleManager.deleteEmailResponseHandle(emailResetHandle);
            return RegistrationResponse.REGISTRATION_SUCCESSFUL;
        }
        return RegistrationResponse.INTERNAL_ERROR;

    }
}
