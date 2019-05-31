package de.digiwill.util;

import org.springframework.lang.NonNull;

public class RegexMatcher {
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=ß!\"§~'`<>|;:,.\\-\\\\\\/\\?])(?=\\S+$).{8,}$";
    private static final String IFTTT_URL_REGEX = "https:\\/\\/maker.ifttt.com\\/trigger\\/.*?\\/with\\/key\\/.{10,}";


    public static boolean isValidEmailAddress(@NonNull String email){
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isValidMultipleEmailAddress(@NonNull String emails){
        String[] emailsArray = emails.split(",");
        if(emailsArray.length <= 0){
            return false;
        }

        for (String email : emailsArray) {
            if(!isValidEmailAddress(email)){
                return false;
            }
        }

        return true;
    }

    public static boolean isValidPassword(@NonNull String password){
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isIFTTTUrl(@NonNull String url) {
        return url.matches(IFTTT_URL_REGEX);
    }
}
