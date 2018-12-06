package de.digiwill;

import de.digiwill.model.BaseAction;
import de.digiwill.model.Security.SecurityHelper;
import de.digiwill.model.UserHandle;
import de.digiwill.model.UserHandleManager;
import de.digiwill.repository.UserHandleRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public abstract class SpringBootBaseIntegrationTest {

    @LocalServerPort
    protected int port;

    @Autowired
    private UserHandleRepository repository;

    private UserHandleManager userHandleManager;

    public SpringBootBaseIntegrationTest() {
    }

    protected void setUpUserHandle(int amount, List<BaseAction> actions) {
        if (userHandleManager == null) {
            userHandleManager = new UserHandleManager(repository);
        }
        List<UserHandle> users = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            UserHandle userHandle = new UserHandle("nobody" + i + "@digiwill.de",
                    SecurityHelper.encodePassword("nobody" + i + "@digiwill.de"), null);
            users.add(userHandle);
        }
        userHandleManager.createUsers(users);
    }

    protected void dropUsers() {
        if (userHandleManager != null) {
            userHandleManager.deleteAllUsers();
        }
    }
}