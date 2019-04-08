package de.digiwill.controller;

import de.digiwill.exception.EmailException;
import de.digiwill.model.BaseAction;
import de.digiwill.model.EmailAction;
import de.digiwill.model.UserHandle;
import de.digiwill.model.UserHandleManager;
import de.digiwill.service.registration.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmailController {

    @Autowired
    private UserHandleManager userHandleManager;
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/addEmail")
    public String addEmail(Model model) {
        return "add_email";
    }

    @PostMapping("/addEmail")
    public RedirectView addEmailPost(@RequestParam(name = "recipients", required = true) String recipients, @RequestParam(name = "subject", required = true) String subject, @RequestParam(name = "content", required = true) String content, Principal principal, Model model) {
        String emailAddress = principal.getName();
        UserHandle user = userHandleManager.loadUserByEmailAddress(emailAddress);
        try {
            EmailAction action = generateEmailAction(recipients, subject, content);
            user.addAction(action);
            userHandleManager.updateUser(user);
            return new RedirectView("getEmails");
        }catch(EmailException e){
            return new RedirectView("getEmails");
        }
    }

    private EmailAction generateEmailAction(@RequestParam(name = "recipients", required = true) String recipients, @RequestParam(name = "subject", required = true) String subject, @RequestParam(name = "content", required = true) String content) throws EmailException {
        List<String> recipient_list = new ArrayList<>();
        String[] recipient_array = recipients.split(" ");
        for (String recipient : recipient_array) {
            if (registrationService.isValidEmailAddress(recipient)) {
                recipient_list.add(recipient);
            } else {
                throw new EmailException("Recipient List contains invalid Email Address");
            }

        }
        return new EmailAction(recipient_list, subject, false, content);
    }

    @GetMapping("/getEmails")
    public String getEmails(Model model, Principal principal) {
        UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
        List<BaseAction> actions = user.getActions();
        List<EmailAction> emails = new ArrayList<>();
        for (BaseAction action : actions
        ) {
            emails.add((EmailAction) action);
        }
        model.addAttribute("emails", emails);
        return "get_emails";
    }

    @GetMapping("/editEmail")
    public String editEmail(@RequestParam(name = "idx", required = true) String index, Principal principal, Model model) {
        int idx = Integer.parseInt(index);
        UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
        List<BaseAction> actions = user.getActions();
        EmailAction action = (EmailAction) actions.get(idx);
        model.addAttribute("email", action);
        return "edit_email";
    }

    @PostMapping("/editEmail")
    public RedirectView editEmailPost(@RequestParam(name = "recipients", required = true) String recipients, @RequestParam(name = "subject", required = true) String subject, @RequestParam(name = "content", required = true) String content, @RequestParam(name = "index", required = true) String index, Principal principal, Model model) {
        int idx = Integer.parseInt(index);
        UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
        List<BaseAction> actions = user.getActions();
        try {
            actions.set(idx, generateEmailAction(recipients, subject, content));
            user.setActions(actions);
            userHandleManager.updateUser(user);
            return new RedirectView("getEmails");
        }catch(EmailException e){
            return new RedirectView("getEmails");
        }
    }

    @GetMapping("/deleteEmail")
    public RedirectView deleteEmail(@RequestParam(name = "idx", required = true) String index, Principal principal) {
        int idx = Integer.parseInt(index);
        UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
        List<BaseAction> actions = user.getActions();
        actions.remove(idx);
        user.setActions(actions);
        userHandleManager.updateUser(user);
        return new RedirectView("getEmails");
    }


}
