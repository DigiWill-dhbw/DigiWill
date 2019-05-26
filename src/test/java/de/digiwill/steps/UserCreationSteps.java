package de.digiwill.steps;

import cucumber.api.java.en.Given;
import de.digiwill.model.*;
import de.digiwill.repository.UserHandleRepository;
import de.digiwill.service.UserHandleManager;
import de.digiwill.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;

public class UserCreationSteps {

    @Autowired
    private UserHandleManager userHandleManager;

    @Autowired
    private UserHandleRepository repository;

    @Given("^A user with email \"([^\"]*)\" and password \"([^\"]*)\" exists$")
    public void aUserWithEmailAndPasswordExists(String email, String password){
        setUpUser(email, password);
    }


    private void setUpUser(String email, String password) {
        PersonalData personalData = new PersonalData("FirstName", "SurName", new Date(1970, 1, 1));
        setUpUser(email, password, personalData);
    }

    private void setUpUser(String email, String password, PersonalData personalData) {
        AuthoritySet authoritySet = new AuthoritySet(AuthorityUtils.createAuthorityList("ROLE_USER"));
        setUpUser(email, password, personalData, authoritySet);
    }

    private void setUpUser(String email, String password, PersonalData personalData, AuthoritySet authoritySet) {
        if (userHandleManager == null) {
            userHandleManager = new UserHandleManager(repository);
        }
        UserBooleans userBooleans = new UserBooleans(true, true, true, true);
        UserHandle userHandle = UserHandleFactory.createCompleteUserHandle(email, SecurityHelper.encodePassword(password), authoritySet,
                userBooleans, UserTimestamps.getInitial(), UserDeltaTimes.getInitial(), false,
                personalData, ActionSet.getInitial());
        userHandleManager.createUser(userHandle);
    }

}
