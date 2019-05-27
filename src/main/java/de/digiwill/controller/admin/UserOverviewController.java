package de.digiwill.controller.admin;

import de.digiwill.model.UserHandle;
import de.digiwill.service.ResetPasswordService;
import de.digiwill.service.UserHandleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserOverviewController {

    private static final String redirectTarget = "redirect:/admin/overview/users";

    @Autowired
    private UserHandleManager userHandleManager;

    @Autowired
    private ResetPasswordService resetPasswordService;

    @GetMapping("/admin/overview/users")
    public String listAllUsers(Model model) {
            model.addAttribute("userhandles", userHandleManager.findAll());
            return "admin/overview/users";
    }

    @RequestMapping(value = "/admin/overview/users/delete", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String delete(@RequestBody MultiValueMap<String, String> formData, Model model) {
            userHandleManager.deleteUser(formData.getFirst("delete"));
            return redirectTarget;
    }

    @RequestMapping(value = "/admin/overview/users/toggleAdmin", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String toggleAdmin(@RequestBody MultiValueMap<String, String> formData, Model model) {
            UserHandle userHandle = userHandleManager.loadUserByEmailAddress(formData.getFirst("emailAddress"));
            GrantedAuthority auth = userHandle.getAuthorityByRoleName("ROLE_ADMIN");
            if (auth == null) {
                userHandle.addAuthority("ROLE_ADMIN");
            } else {
                userHandle.removeAuthority(auth);
            }
            userHandleManager.updateUser(userHandle);

            return redirectTarget;
    }

    @RequestMapping(value = "/admin/overview/users/requestPasswordReset", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String requestPasswordReset(@RequestBody MultiValueMap<String, String> formData) {
        resetPasswordService.requestPasswordReset(formData);
        return redirectTarget;
    }

}
