package de.digiwill.controller;

import de.digiwill.model.UserHandle;
import de.digiwill.service.ProfileService;
import de.digiwill.service.validation.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.text.SimpleDateFormat;

@Controller
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile")
    public String profile(Principal principal, Model model, RedirectAttributes redirectAttrs) {
        addDataToProfilePageModel(principal, model, false);

        return "profile";
    }

    @GetMapping("/editProfile")
    public String editProfile(Principal principal, Model model, RedirectAttributes redirectAttrs) {
        addDataToProfilePageModel(principal, model, true);

        return "profile";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView changePassword(@RequestBody MultiValueMap<String, String> formData, Model model, Principal principal, RedirectAttributes redirectAttrs) {
        ValidationResponse response = profileService.changePassword(formData, principal.getName());
        response.adjustModel(model);
        addFlashAttributesToRedirectAttributes(redirectAttrs, response);
        return new ModelAndView(response.isSuccess() ? "redirect:/profile" : "redirect:/editProfile");
    }

    @RequestMapping(value = "/editProfile", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView saveProfile(@RequestBody MultiValueMap<String, String> formData, Model model, Principal principal, RedirectAttributes redirectAttrs) {
        ValidationResponse response = profileService.editUser(formData, principal.getName());
        response.adjustModel(model);
        addFlashAttributesToRedirectAttributes(redirectAttrs, response);
        return new ModelAndView(response.isSuccess() ? "redirect:/profile" : "redirect:/editProfile");
    }

    private void addFlashAttributesToRedirectAttributes(RedirectAttributes redirectAttrs, ValidationResponse response) {
        redirectAttrs.addFlashAttribute("edit", !response.isSuccess());
        redirectAttrs.addFlashAttribute("hasToast", !response.isSuccess());
        redirectAttrs.addFlashAttribute("responseText", response.getFailureMessage());
    }

    private void addDataToProfilePageModel(Principal principal, Model model, boolean edit) {
        String emailAddress = principal.getName();
        UserHandle user = profileService.getUserHandleByEmail(emailAddress);
        SimpleDateFormat dateFormat = new SimpleDateFormat(edit ? "yyyy-MM-dd" : "dd.MM.yyyy");
        model.addAttribute("email", emailAddress);
        model.addAttribute("dateOfBirth", dateFormat.format(user.getPersonalData().getDateOfBirth()));
        model.addAttribute("personalData", user.getPersonalData());
        if (!model.containsAttribute("edit")) {
            model.addAttribute("edit", edit);
        }
    }

}
