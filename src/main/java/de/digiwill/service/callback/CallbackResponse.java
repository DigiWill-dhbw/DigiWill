package de.digiwill.service.callback;

import org.springframework.ui.Model;

public enum CallbackResponse {

    CALLBACK_VERIFICATION_SUCCESS(true, null, "verificationSuccess"),
    CALLBACK_RESET_PASSWORD(true, null, "resetPassword"),
    CALLBACK_ERROR(false,  "Callback URL parameters are invalid!", "callbackError");

    private boolean success;
    private String responseText;
    private String longText;
    private String redirect;

    CallbackResponse(boolean success, String responseText, String redirect) {
        this.success = success;
        this.responseText = responseText;
        this.redirect = redirect;
        if(success){
            this.longText = "Callback successful! Jannik möchte hier eine lustige Nachricht!";
        }else{
            this.longText = "Callback error. Link could have been already used and is invalid.";
        }
    }

    public void adjustModel(final Model model, String id, String token){
        model.addAttribute("id", id);
        model.addAttribute("token", token);
        model.addAttribute("failure", !success);
        model.addAttribute("longText", longText);
        if(responseText != null) {
            model.addAttribute("responseText", responseText );
        }
    }

    public String getRedirectTarget() {
        return redirect;
    }

}
