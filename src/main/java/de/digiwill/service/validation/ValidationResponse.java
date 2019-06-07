package de.digiwill.service.validation;

import org.springframework.ui.Model;

public enum ValidationResponse {

    SUCCESSFUL(true, null),
    PASSWORD_MISMATCH(false,  "The entered passwords don't match"),
    PASSWORD_REQUIREMENTS_NOT_MET(false, "The password requirements weren't met"),
    INVALID_EMAIL_ADDRESS(false, "Please enter a valid email address"),
    INVALID_PASSWORD(false, "The entered password is not correct"),
    NO_FIRST_NAME(false, "Please enter a first name"),
    NO_SURNAME(false, "Please enter a surname"),
    TO_YOUNG(false, "You must be at least XXX years old to register for this service"),
    EMAIL_ALREADY_IN_USE(false, "There already is an account registered with the email address you're trying to use"),
    BIRTHDAY_INVALID(false, "Please enter a valid date of birth"),
    FORM_DATA_DOESNT_EXIST(false, "The data you entered wasn't transmitted to out server"),
    INTERNAL_ERROR(false, "An internal server error occurred");

    private boolean success;
    private String failureMessage;

    ValidationResponse(boolean success, String failureMessage) {
        this.success = success;
        this.failureMessage = failureMessage;
    }

    public void adjustModel(final Model model){
        if(failureMessage != null){
            model.addAttribute("hasToast", true);
            model.addAttribute("responseText", failureMessage);
        }
    }

    public String getRedirectTarget(String successRedirect, String noSuccessRedirect) {
        return success ? successRedirect : noSuccessRedirect;
    }
}
