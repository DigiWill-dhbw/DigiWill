package de.digiwill;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import de.digiwill.service.UserHandleManager;
import de.digiwill.repository.UserHandleRepository;
import de.digiwill.util.ChromeWebDriver;
import de.digiwill.util.FirefoxWebDriver;
import de.digiwill.util.SeleniumDriverUtils;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
