package de.digiwill.model.Security;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityHelper {

    private static PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static String encodePassword(String password){
        return encoder.encode(password); //TODO
    }
}
