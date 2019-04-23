package de.digiwill.service.callback;

import org.springframework.ui.Model;

public enum CallbackResponse {

    CALLBACK_SUCCESS(true, null),
    CALLBACK_ERROR(false,  "Callback URL parameters are invalid!");
    //PLACE FOR OTHER ERRORS

    private boolean success;
    private String responseText;
    private String longText;

    CallbackResponse(boolean success, String responseText) {
        this.success = success;
        this.responseText = responseText;
        if(success){
            this.longText = "Callback successful! Jannik möchte hier eine lustige Nachricht!";
        }else{
            this.longText = "Callback error. Link could have been already used and is invalid.";
        }
    }

    public void adjustModel(final Model model){
        model.addAttribute("failure", !success);
        model.addAttribute("longText", longText);
        if(responseText != null) {
            model.addAttribute("responseText", responseText );
        }
    }

    public String getRedirectTarget() {
        return "callback";
    }

}
