package de.digiwill;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import de.digiwill.model.*;
import de.digiwill.service.UserHandleManager;
import de.digiwill.repository.UserHandleRepository;
import de.digiwill.util.TestUtils;
import de.digiwill.util.SecurityHelper;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
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
    @Autowired
    private UserHandleManager userHandleManager;

    @Configuration
    @Import(Application.class)
    public static class TestConfig {
        @Bean
        public MongoTemplate mongoTemplate() throws IOException {
            EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
            mongo.setBindIp("localhost");
            MongoClient mongoClient = mongo.getObject();
            MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "users");
            return mongoTemplate;
        }
    }

    public void setUpUserHandle(int amount, List<BaseAction> actions) {
        if (userHandleManager == null) {
            userHandleManager = new UserHandleManager(repository);
        }
        List<UserHandle> users = TestUtils.createUserHandles(amount, actions);
        userHandleManager.createUsers(users);
    }

    public void setUpSingleUser(String email, String password) {
        if (userHandleManager == null) {
            userHandleManager = new UserHandleManager(repository);
        }
        PersonalData personalData = new PersonalData("no", "body", new Date(1990, 1, 1));
        UserBooleans userBooleans = new UserBooleans(true, true, true, true);
        UserHandle userHandle = new UserHandle(email, SecurityHelper.encodePassword(password), AuthorityUtils.createAuthorityList("ROLE_USER"),
                userBooleans, -1, -1, -1, -1, false,
                personalData, UserActionSet.getInitial());
        userHandleManager.createUser(userHandle);
    }

    public void dropUsers() {
        if (userHandleManager != null) {
            userHandleManager.deleteAllUsers();
        }
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public int getPort() {
        return port;
    }

    public UserHandleRepository getRepository() {
        return repository;
    }

    public UserHandleManager getUserHandleManager() {
        return userHandleManager;
    }
}
