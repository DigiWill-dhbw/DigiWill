package de.digiwill;

import de.digiwill.model.BaseAction;
import de.digiwill.model.PersonalData;
import de.digiwill.util.SecurityHelper;
import de.digiwill.model.UserHandle;
import de.digiwill.model.UserHandleManager;
import de.digiwill.repository.UserHandleRepository;
import de.digiwill.util.TestUtils;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataMongo
public abstract class SpringBootBaseIntegrationTest {

    private WebDriver webDriver;

    @LocalServerPort
    protected int port;

    @Autowired
    private UserHandleRepository repository;
    private UserHandleManager userHandleManager;


    public void setUpUserHandle(int amount, List<BaseAction> actions) {
        if (userHandleManager == null) {
            userHandleManager = new UserHandleManager(repository);
        }
        List<UserHandle> users = TestUtils.createUserHandles(amount, actions);
        userHandleManager.createUsers(users);
    }

    public void setUpSingleUser(String email, String password){
        if (userHandleManager == null) {
            userHandleManager = new UserHandleManager(repository);
        }
        List<BaseAction> actions = new ArrayList<>();
        PersonalData personalData = new PersonalData("no", "body", new Date(1990, 1, 1));
        UserHandle userHandle = new UserHandle(email, SecurityHelper.encodePassword(password), AuthorityUtils.createAuthorityList("ROLE_USER"),
                true, true, true,
                true , -1, -1, -1, -1, false,
                personalData,   actions, false);
        userHandleManager.createUser(userHandle);
    }

    public void dropUsers() {
        if (userHandleManager != null) {
            userHandleManager.deleteAllUsers();
        }
    }

    public void setWebDriver(WebDriver webDriver){
        this.webDriver = webDriver;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public int getPort(){
        return port;
    }

    public UserHandleRepository getRepository() {
        return repository;
    }

    public UserHandleManager getUserHandleManager() {
        return userHandleManager;
    }
}