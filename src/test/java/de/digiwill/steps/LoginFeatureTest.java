package de.digiwill.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.digiwill.SpringBootBaseIntegrationTest;
import de.digiwill.model.EmailAction;
import de.digiwill.model.UserHandle;
import de.digiwill.model.UserHandleManager;
import de.digiwill.util.SeleniumDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class LoginFeatureTest {
    @Autowired
    private SpringBootBaseIntegrationTest springBootBaseIntegrationTest;

    @Autowired
    private UserHandleManager userHandleManager;


    @Given("^\"([^\"]*)\" Users are created$")
    public void createUsers(String arg0) {
        springBootBaseIntegrationTest.setUpUserHandle(Integer.parseInt(arg0), Collections.singletonList(
                new EmailAction(Collections.singletonList("nobodyT@digiwill.de"), "Hey there!", false, "blalbalbla")
        ));
    }

    @Given("^\"([^\"]*)\" is open$")
    public void theIsOpen(String url){
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        driver.get("http://localhost:" + springBootBaseIntegrationTest.getPort() + url);
    }

    @Given("^A user with email \"([^\"]*)\" and password \"([^\"]*)\" exists$")
    public void aUserWithEmailAndPasswordExists(String email, String password){
        springBootBaseIntegrationTest.setUpSingleUser(email, password);
    }

    @Given("^A user with email \"([^\"]*)\" doesn't exist$")
    public void aUserWithEmailAndPasswordDoesntExist(String email){
        try {
            UserHandle userHandle = userHandleManager.loadUserByEmailAddress(email);
            if (userHandle != null) {
                userHandleManager.deleteUser(email);
            }
        }catch(UsernameNotFoundException ignore){}
    }

    @When("^Enter Email \"([^\"]*)\", password \"([^\"]*)\" and login$")
    public void enterEmailPasswordAndLogin(String emailAddress, String password) {
        // Write code here that turns the phrase above into concrete actions
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        driver.findElement(By.id("emailInput")).sendKeys(emailAddress);
        driver.findElement(By.id("passwordInput")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
    }

    @Then("^Login \"([^\"]*)\"$")
    public void login(String state) {
        // Write code here that turns the phrase above into concrete actions
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        switch (state) {
            case "succeeds":
                assertEquals("http://localhost:" + springBootBaseIntegrationTest.getPort() + "/", driver.getCurrentUrl());
                break;
            case "fails":
                assertEquals("http://localhost:" + springBootBaseIntegrationTest.getPort() + "/?login&error", driver.getCurrentUrl());
                break;
            default:
                fail("Invalid argument: 'Login \"" + state + "\"");
                break;
        }
    }

    @Then("Logout was successful")
    public void logoutWasSuccessful() {
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        driver.get("http://localhost:" + springBootBaseIntegrationTest.getPort());
        assertNotNull(driver.findElement(By.id("loginButton")));
    }

    @Before("@uitest")
    public void beforeUITest() {
        System.setProperty("webdriver.chrome.driver", SeleniumDriverUtils.getChromeDriverPath());
        springBootBaseIntegrationTest.setWebDriver(new ChromeDriver(SeleniumDriverUtils.getChromeOptions()));
    }

    @After("@uitest")
    public void afterUITest() {
        springBootBaseIntegrationTest.getWebDriver().quit();
        springBootBaseIntegrationTest.dropUsers();
    }
}
