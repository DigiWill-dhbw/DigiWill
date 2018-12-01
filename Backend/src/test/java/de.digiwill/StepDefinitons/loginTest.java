package de.digiwill.StepDefinitons;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class loginTest {

    private String pathToChromeDriver = "src\\test\\resources\\chromedriver.exe";
    private WebDriver driver;

    @Given("^\"([^\"]*)\" is open$")
    public void theIsOpen(String url) throws Throwable {
        System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(url);
        //throw new PendingException();
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

        //throw new PendingException();
    }

    @And("^Close Session$")
    public void closeSession() {
        driver.quit();
    }

    @Then("^Login for \"([^\"]*)\", \"([^\"]*)\"$")
    public void loginFor(String user, String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        switch (arg1) {
            case "succeeds":
                assertEquals("http://localhost:8080/", driver.getCurrentUrl());
                break;
            case "fails":
                assertEquals("http://localhost:8080/login?error", driver.getCurrentUrl());
                break;
        }
    }
}
