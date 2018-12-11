package de.digiwill.controller;

import de.digiwill.model.BaseAction;
import de.digiwill.model.EmailAction;
import de.digiwill.model.UserHandle;
import de.digiwill.repository.UserHandleRepository;
import de.digiwill.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmailController {
    @Autowired
    UserHandleRepository repository;
    @Autowired
    RegistrationService registrationService;

    @GetMapping("/addEmail")
    public String addEmail(Model model) {
        return "add_email";
    }
    @PostMapping("/addEmail")
    public RedirectView addEmailPost(@RequestParam(name="recipients", required=true) String recipients, @RequestParam(name="subject", required=true) String subject, @RequestParam(name="content", required=true) String content, Principal principal, Model model){
        String username = principal.getName();
        UserHandle user = repository.findUserHandleByUsername(username);
        List<String> recipient_list = new ArrayList<>();
        String[] reci_array = recipients.split(" ");
        for (String r :
                reci_array) {
            if (registrationService.isValidEmailAdress(r)) {
                recipient_list.add(r);
            } else {
                return new RedirectView("getEmails");
            }

        }
        EmailAction action = new EmailAction(recipient_list, subject, false, content);
        user.addAction(action);
        repository.save(user);
        return new RedirectView("getEmails");
    }
    @GetMapping("/getEmails")
    public String getEmails(Model model, Principal principal) {
        UserHandle user = repository.findUserHandleByUsername(principal.getName());
        List<BaseAction> actions= user.getActions();
        List<EmailAction> emails = new ArrayList<>();
        for (BaseAction action: actions
        ) {
            emails.add((EmailAction) action);
        }
        model.addAttribute("emails", emails);
        return "get_emails";
    }
    @GetMapping("/editEmail")
    public String editEmail(@RequestParam(name="idx", required=true) String index, Principal principal, Model model) {
        int idx = Integer.parseInt(index);
        UserHandle user = repository.findUserHandleByUsername(principal.getName());
        List<BaseAction> actions= user.getActions();
        EmailAction action = (EmailAction) actions.get(idx);
        model.addAttribute("email", action);
        return "edit_email";
    }
    @PostMapping("/editEmail")
    public RedirectView editEmailPost(@RequestParam(name="recipients", required=true) String recipients, @RequestParam(name="subject", required=true) String subject, @RequestParam(name="content", required=true) String content, @RequestParam(name="index", required=true) String index,Principal principal, Model model) {
        int idx = Integer.parseInt(index);
        UserHandle user = repository.findUserHandleByUsername(principal.getName());
        List<BaseAction> actions= user.getActions();
        List<String> recipient_list = new ArrayList<>();
        String[] reci_array = recipients.split(" ");
        for (String r :
                reci_array) {
            if (registrationService.isValidEmailAdress(r)) {
                recipient_list.add(r);
            } else {
                return new RedirectView("getEmails");
            }
        }
        EmailAction action = new EmailAction(recipient_list, subject, false, content);
        actions.set(idx, action);
        user.setActions(actions);
        repository.save(user);
        return new RedirectView("getEmails");
    }
    @GetMapping("/deleteEmail")
    public RedirectView deleteEmail(@RequestParam(name="idx", required=true) String index, Principal principal) {
        int idx = Integer.parseInt(index);
        UserHandle user = repository.findUserHandleByUsername(principal.getName());
        List<BaseAction> actions= user.getActions();
        actions.remove(idx);
        user.setActions(actions);
        repository.save(user);
        return new RedirectView("getEmails");
    }


}
