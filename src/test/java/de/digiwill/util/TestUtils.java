package de.digiwill.util;

import de.digiwill.model.*;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestUtils {

    public static List<UserHandle> createUserHandles(int amount, List<BaseAction> actions) {
        List<UserHandle> users = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            PersonalData personalData = new PersonalData("no", "body" + i, new Date(2018, 1, 1));
            UserBooleans userBooleans = new UserBooleans(true, true, true, true);
            UserHandle userHandle = new UserHandle("nobody" + i + "@digiwill.de", SecurityHelper.encodePassword("nobody" + i + "@digiwill.de"), new AuthoritySet(AuthorityUtils.createAuthorityList("ROLE_USER")),
                    userBooleans, UserTimestamps.getInitial(), -1, -1, false,
                    personalData,   new UserActionSet(actions, false));
            users.add(userHandle);
        }
        return users;
    }
}
