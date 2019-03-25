package de.digiwill.model.Security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SecurityHelper {

    private static PasswordEncoder encoder = new BCryptPasswordEncoder(11);

    public static String encodePassword(String password) {
        return encoder.encode(password);
    }

    public static PasswordEncoder getEncoder() {
        return encoder;
    }
}
