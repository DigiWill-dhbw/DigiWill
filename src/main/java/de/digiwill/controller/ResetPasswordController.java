package de.digiwill.controller;

import de.digiwill.exception.EmailException;
import de.digiwill.model.EmailResetHandle;
import de.digiwill.model.EmailVerificationHandle;
import de.digiwill.model.UserHandle;
import de.digiwill.repository.EmailResponseHandleRepository;
import de.digiwill.service.EmailDispatcher;
import de.digiwill.service.UserHandleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResetPasswordController {

    @Autowired
    private UserHandleManager userHandleManager;

    @Autowired
    private EmailDispatcher emailDispatcher;
    @Autowired
    private EmailResponseHandleRepository emailResponseHandleRepository;


    @RequestMapping(value = "/requestPasswordReset", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String requestPasswordReset(@RequestBody MultiValueMap<String, String> formData, Model model, RedirectAttributes redirectAttrs) {

        try {
            UserHandle userHandle = userHandleManager.loadUserByEmailAddress(formData.getFirst("email"));
            EmailResetHandle emailResetHandle = new EmailResetHandle(userHandle);
            emailResponseHandleRepository.insert(emailResetHandle);

            emailDispatcher.sendResetEmail(emailResetHandle, userHandle);
        } catch (EmailException e) {
        } catch (Exception e) {
        }
        return "redirect:/";
    }

}
