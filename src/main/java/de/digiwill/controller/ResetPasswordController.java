package de.digiwill.controller;

import de.digiwill.service.ResetPasswordService;
import de.digiwill.service.validation.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping(value = "/requestPasswordReset", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String requestPasswordReset(@RequestBody MultiValueMap<String, String> formData) {
        resetPasswordService.requestPasswordReset(formData);
        return "redirect:/";
    }

    @PostMapping(value = "/resetPassword", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String resetPassword(@RequestBody MultiValueMap<String, String> formData, Model model) {
        ValidationResponse response = resetPasswordService.resetPassword(formData);
        if(response.equals(ValidationResponse.SUCCESSFUL)){
            return "redirect:/";
        }else{
            response.adjustModel(model);
            model.addAttribute("id", formData.getFirst("id"));
            model.addAttribute("token", formData.getFirst("token"));
            return "resetPassword";
        }
    }

}
