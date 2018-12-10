package de.digiwill.ui;

import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.digiwill.SpringBootBaseIntegrationTest;
import de.digiwill.model.EmailAction;
import de.digiwill.util.SeleniumDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class loginTest{
    @Autowired
    SpringBootBaseIntegrationTest springBootBaseIntegrationTest;

    WebDriver driver;

    @Given("^\"([^\"]*)\" Users are created$")
    public void createUsers(String arg0) {
        springBootBaseIntegrationTest.setUpUserHandle(Integer.parseInt(arg0), Arrays.asList(
                new EmailAction(Arrays.asList("nobodyT@digiwill.de"), "Hey there!", false, "blalbalbla")
        ));
    }

    @Given("^\"([^\"]*)\" is open$")
    public void theIsOpen(String url) throws Throwable {
        driver.get("http://localhost:" + springBootBaseIntegrationTest.getPort() + url);
    }

    @Given("^A user with email \"([^\"]*)\" and password \"([^\"]*)\" \"([^\"]*)\"$")
    public void aUserWithEmailAndPassword(String arg0, String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //TODO
        //throw new PendingException();
    }

    @When("^Enter Email \"([^\"]*)\", password \"([^\"]*)\" and login$")
    public void enterEmailPasswordAndLogin(String emailAddress, String password) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        driver.findElement(By.id("usernameInput")).sendKeys(emailAddress);
        driver.findElement(By.id("passwordInput")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
    }

    @Then("^Login for \"([^\"]*)\", \"([^\"]*)\"$")
    public void loginFor(String user, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        switch (arg1) {
            case "succeeds":
                assertEquals("http://localhost:" + springBootBaseIntegrationTest.getPort() + "/login", driver.getCurrentUrl());
                break;
            case "fails":
                assertEquals("http://localhost:" + springBootBaseIntegrationTest.getPort() + "/?login&error", driver.getCurrentUrl());
                break;
        }
    }

    @Before("@uitest")
    public void beforeUITest(){
        System.setProperty("webdriver.chrome.driver", SeleniumDriverUtils.getChromeDriverPath());
        driver = new ChromeDriver(SeleniumDriverUtils.getChromeOptions());
    }

    @After("@uitest")
    public void afterUITest() {
        driver.close();
        springBootBaseIntegrationTest.dropUsers();
    }

}
