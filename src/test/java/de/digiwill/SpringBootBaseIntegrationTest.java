package de.digiwill;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import de.digiwill.model.*;
import de.digiwill.repository.UserHandleRepository;
import de.digiwill.service.UserHandleManager;
import de.digiwill.util.ChromeWebDriver;
import de.digiwill.util.FirefoxWebDriver;
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
import java.util.Calendar;
import java.util.GregorianCalendar;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureDataMongo
public abstract class SpringBootBaseIntegrationTest {

    @Autowired
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

        @Bean(name = "webDriver", destroyMethod = "close")
        public WebDriver getWebDriver() {
            WebDriver webdriver = null;

            switch (System.getProperty("browser", "chrome")) {
                case "chrome":
                    webdriver = new ChromeWebDriver();
                    break;
                case "firefox":
                    webdriver = new FirefoxWebDriver();
                    break;
                default:
                    break;
            }
            return webdriver;
        }

        @Bean
        public GreenMail getGreenMail() {
            ServerSetup serverSetup = ServerSetupTest.SMTP;
            serverSetup.setVerbose(true);
            GreenMail greenMail = new GreenMail(serverSetup);
            greenMail.setUser("digiwill", "password");
            greenMail.start();
            return greenMail;
        }
    }

    public void setUpUser(String email, String password) {
        PersonalData personalData = new PersonalData("FirstName", "SurName", new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime());
        setUpUser(email, password, personalData);
    }

    public void setUpUser(String email, String password, PersonalData personalData) {
        AuthoritySet authoritySet = new AuthoritySet(AuthorityUtils.createAuthorityList("ROLE_USER"));
        setUpUser(email, password, personalData, authoritySet);
    }

    public void setUpAdmin(String email, String password) {
        PersonalData personalData = new PersonalData("Admin", "McAdminface", new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime());
        AuthoritySet authoritySet = new AuthoritySet(AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN"));
        setUpUser(email, password, personalData, authoritySet);
    }

    public void setUpUser(String email, String password, PersonalData personalData, AuthoritySet authoritySet) {
        ActionSet actionSet = ActionSet.getInitial();
        setUpUser(email, password, personalData, authoritySet, actionSet);
    }

    public void setUpUser(String email, String password, PersonalData personalData, AuthoritySet authoritySet, ActionSet actionSet) {
        if (userHandleManager == null) {
            userHandleManager = new UserHandleManager(repository);
        }
        userHandleManager.createUser(generateUser(email, password, personalData, authoritySet, actionSet));
    }

    public static UserHandle generateUser(String email, String password, ActionSet actionSet) {
        PersonalData personalData = new PersonalData("FirstName", "SurName", new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime());
        AuthoritySet authoritySet = new AuthoritySet(AuthorityUtils.createAuthorityList("ROLE_USER"));
        return generateUser(email, password, personalData, authoritySet, actionSet);
    }

    public static UserHandle generateUser(String email, String password, PersonalData personalData, AuthoritySet authoritySet, ActionSet actionSet){
        UserBooleans userBooleans = new UserBooleans(true, true, true, true);
        UserHandle userHandle = new UserHandle(email, SecurityHelper.encodePassword(password), authoritySet,
                userBooleans, UserTimestamps.getInitial(), UserDeltaTimes.getInitial(), false,
                personalData, actionSet);
        return userHandle;
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
