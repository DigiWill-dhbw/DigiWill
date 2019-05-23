package de.digiwill.controller;

import de.digiwill.service.ResetPasswordService;
import de.digiwill.service.registration.RegistrationResponse;
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
    ResetPasswordService resetPasswordService;

    @RequestMapping(value = "/requestPasswordReset", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String requestPasswordReset(@RequestBody MultiValueMap<String, String> formData) {
        resetPasswordService.requestPasswordReset(formData);
        return "redirect:/";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String resetPassword(@RequestBody MultiValueMap<String, String> formData, Model model) {
        RegistrationResponse response = resetPasswordService.resetPassword(formData);
        if(response.equals(RegistrationResponse.REGISTRATION_SUCCESSFUL)){
            return "redirect:/";
        }else{
            response.adjustModel(model);
            model.addAttribute("id", formData.getFirst("id"));
            model.addAttribute("token", formData.getFirst("token"));
            return "/resetPassword";
        }
    }

}
