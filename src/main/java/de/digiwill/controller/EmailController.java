package de.digiwill.controller;

import de.digiwill.exception.EmailException;
import de.digiwill.model.BaseAction;
import de.digiwill.model.EmailAction;
import de.digiwill.model.UserHandle;
import de.digiwill.service.UserHandleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.List;

@Controller
public class EmailController {

    public static final String EMAIL_OVERVIEW_URL = "getEmails";

    @Autowired
    private UserHandleManager userHandleManager;

    @GetMapping("/addEmail")
    public String addEmail(Model model) {
        return "add_email";
    }

    @PostMapping("/addEmail")
    public RedirectView addEmailPost(@RequestParam(name = "recipients", required = true) String recipients, @RequestParam(name = "subject", required = true) String subject, @RequestParam(name = "content", required = true) String content, Principal principal, Model model) {
        String emailAddress = principal.getName();
        UserHandle user = userHandleManager.loadUserByEmailAddress(emailAddress);
        try {
            EmailAction action = EmailAction.generateEmailAction(recipients, subject, content);
            user.addAction(action);
            userHandleManager.updateUser(user);
            return new RedirectView(EMAIL_OVERVIEW_URL);
        }catch(EmailException e){
            return new RedirectView(EMAIL_OVERVIEW_URL);
        }
    }

    @GetMapping("/getEmails")
    public String getEmails(Model model, Principal principal) {
        UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
        List<EmailAction> emails = user.getEmailActions();
        model.addAttribute("emails", emails);
        return "get_emails";
    }

    @GetMapping("/editEmail")
    public String editEmail(@RequestParam(name = "idx", required = true) String index, Principal principal, Model model) {
        int idx = Integer.parseInt(index);
        UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
        List<BaseAction> actions = user.getActions();
        EmailAction action = (EmailAction) actions.get(user.getTotalActionIdxFromEmailIdx(idx));
        model.addAttribute("email", action);
        return "edit_email";
    }

    @PostMapping("/editEmail")
    public RedirectView editEmailPost(@RequestParam(name = "recipients", required = true) String recipients, @RequestParam(name = "subject", required = true) String subject, @RequestParam(name = "content", required = true) String content, @RequestParam(name = "index", required = true) String index, Principal principal, Model model) {
        int idx = Integer.parseInt(index);
        UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
        try {
            user.replaceAction(idx, EmailAction.generateEmailAction(recipients, subject, content));
            userHandleManager.updateUser(user);
            return new RedirectView();
        }catch(EmailException e){
            return new RedirectView(EMAIL_OVERVIEW_URL);
        }
    }

    @PostMapping("/deleteEmail")
    public RedirectView deleteEmail(@RequestParam(name = "idx", required = true) String index, Principal principal) {
        int idx = Integer.parseInt(index);
        UserHandle user = userHandleManager.loadUserByEmailAddress(principal.getName());
        user.removeAction(user.getTotalActionIdxFromEmailIdx(idx));
        userHandleManager.updateUser(user);
        return new RedirectView(EMAIL_OVERVIEW_URL);
    }


}
