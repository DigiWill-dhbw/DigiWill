package de.digiwill.controller.admin;

import de.digiwill.model.UserHandle;
import de.digiwill.model.UserHandleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserOverviewController {

    @Autowired
    UserHandleManager userHandleManager;

    @GetMapping("/admin/overview/users")
    public String addPagePerson(Model model) {
        model.addAttribute("userhandles", userHandleManager.findAll());
        return "admin/overview/users";
    }
}
