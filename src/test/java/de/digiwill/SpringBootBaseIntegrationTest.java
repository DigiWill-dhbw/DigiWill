package de.digiwill;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import de.digiwill.repository.UserHandleRepository;
import de.digiwill.service.UserHandleManager;
import de.digiwill.util.ChromeWebDriver;
import de.digiwill.util.FirefoxWebDriver;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

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
        public GreenMail getGreenMail(){
            ServerSetup serverSetup = ServerSetup.SMTP;
            serverSetup.setVerbose(true);
            GreenMail greenMail = new GreenMail(serverSetup);
            greenMail.setUser("digiwill", "password");
            greenMail.start();
            return greenMail;
        }
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
