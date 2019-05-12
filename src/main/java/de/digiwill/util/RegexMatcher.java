package de.digiwill.util;

import org.springframework.lang.NonNull;

public class RegexMatcher {
    private static final String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private static final String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=ß!\"§~'`<>|;:,.\\-\\\\\\/\\?])(?=\\S+$).{8,}$";
    public static final String EMAIL_ADDRESSES_REGEX = "^([A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,},?)+$";
    private static final String iftttURLRegex = "https:\\/\\/maker.ifttt.com\\/trigger\\/.*?\\/with\\/key\\/.{10,}";


    public static boolean isValidEmailAddress(@NonNull String email){
        return email.matches(emailRegex);
    }

    public static boolean isValidMultipleEmailAddress(@NonNull String email){
        return email.matches(EMAIL_ADDRESSES_REGEX);
    }

    public static boolean isValidPassword(@NonNull String password){
        return password.matches(passwordRegex);
    }

    public static boolean isIFTTTUrl(@NonNull String url) {
        return url.matches(iftttURLRegex);
    }
}
