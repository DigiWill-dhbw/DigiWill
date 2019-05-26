package de.digiwill.model;

public class UserHandleFactory {

    public static UserHandle createEmptyUserHandle() {
        return new UserHandle();
    }

    public static UserHandle createUserHandleWithEmailPasswordAuthorities(String emailAddress, String password,
                                                                          AuthoritySet authorities) {
        return new UserHandle(emailAddress, password, authorities);
    }

    public static UserHandle createUserHandleWithEmailPasswordAuthoritiesPersonalData(String emailAddress,
                                                                                      String password,
                                                                                      PersonalData personalData,
                                                                                      AuthoritySet authorities) {
        return new UserHandle(emailAddress, password, personalData, authorities);
    }

    public static UserHandle createCompleteUserHandle(String emailAddress, String password, AuthoritySet authorities,
                                                      UserBooleans userBooleans, UserTimestamps timestamps,
                                                      UserDeltaTimes deltaTimes, boolean isDead,
                                                      PersonalData personalData, ActionSet actionSet) {
        return new UserHandle(emailAddress, password, authorities, userBooleans, timestamps, deltaTimes, isDead,
                personalData, actionSet);
    }

}
