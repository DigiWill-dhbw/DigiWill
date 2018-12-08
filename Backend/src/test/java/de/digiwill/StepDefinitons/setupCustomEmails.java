package de.digiwill.StepDefinitons;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.digiwill.model.BaseAction;
import de.digiwill.model.EmailAction;
import de.digiwill.model.PersonalData;
import de.digiwill.model.Security.SecurityHelper;
import de.digiwill.model.UserHandle;
import de.digiwill.repository.UserHandleRepository;
import de.digiwill.util.SeleniumDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class setupCustomEmails {

    private WebDriver driver;
    @Autowired
    UserHandleRepository repository;
    private UserHandle userHandle;

    @Given("^\"([^\"]*)\" is open")
    public void givenWebsiteopen(String url) throws Throwable {
        System.setProperty("webdriver.chrome.driver", SeleniumDriverUtils.getChromeDriverPath());
        driver = new ChromeDriver();
        //driver.manage().window().maximize();
        driver.get(url);
    }

    @And("^The user \"([^\"]*)\" with the password \"([^\"]*)\" is logged in and on the action overview page$")
    public void theUserWithThePasswordIsLoggedInAndOnTheActionOverviewPage(String email, String password) throws Throwable {
        List<BaseAction> actions = new ArrayList<>();
        PersonalData personalData = new PersonalData("no", "body", new Date(2018, 1, 1));
        userHandle = new UserHandle(email, SecurityHelper.encodePassword(password), null,
                true, true, true, true, 0, 0, 0, 0, false, personalData, actions);
        repository.insert(userHandle);
        driver.findElement(By.id("loginButtonHeader")).click();
        driver.findElement(By.id("usernameInput")).sendKeys(email);
        driver.findElement(By.id("passwordInput")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
        driver.get("http://localhost:8080/getEmails");
    }


    @And("^There are no Email Actions$")
    public void thereAreNoEmailActions() {

    }

    @When("^Create new email action with recipient \"([^\"]*)\", subject \"([^\"]*)\", content \"([^\"]*)\" and click \"([^\"]*)\"$")
    public void createNewEmailActionWithRecipientSubjectContentAndClick(String recipient, String subject, String content, String button) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        driver.get("http://localhost:8080/addEmail");
        driver.findElement(By.id("adressfield")).sendKeys(recipient);
        driver.findElement(By.name("subject")).sendKeys(subject);
        driver.findElement(By.name("content")).sendKeys(content);
        if(button.equals("Save")) {
            driver.findElement(By.id("submitButton")).click();
        } else if(button.equals("Cancel")) {
            driver.findElement(By.id("cancelButton")).click();
        }
    }

    @Then("^The service should accept the new action$")
    public void theServiceShouldAcceptTheNewAction() {
        assertEquals("http://localhost:8080/getEmails", driver.getCurrentUrl());
    }

    @And("^A new item with recipient \"([^\"]*)\", subject \"([^\"]*)\", content \"([^\"]*)\" should exist$")
    public void aNewItemWithRecipientSubjectContentShouldExist(String recipient, String subject, String content) throws Throwable {
        driver.get("http://localhost:8080/getEmails");
        WebElement listing = driver.findElement(By.className("listing"));
        List<WebElement> items = listing.findElements(By.tagName("tr"));
        Boolean found = false;
        for (WebElement item :
                items) {
            //Check if any item in the list is the item expected
            if (recipient.equals(item.findElements(By.tagName("tr")).get(0).getText()) && subject.equals(item.findElements(By.tagName("tr")).get(1).getText()) && content.equals(item.findElements(By.tagName("tr")).get(2).getText())) {
                found = true;
            }
        }
        assertEquals(true, found);
    }

    @And("^No new item with recipient \"([^\"]*)\", subject \"([^\"]*)\", content \"([^\"]*)\" should exist$")
    public void noNewItemWithRecipientSubjectContentShouldExist(String recipient, String subject, String content) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        driver.get("http://localhost:8080/getEmails");
        WebElement listing = driver.findElement(By.className("listing"));
        List<WebElement> items = listing.findElements(By.tagName("tr"));
        Boolean found = false;
        for (WebElement item :
                items) {
            //Check if any item in the list is the item expected
            if (recipient.equals(item.findElements(By.tagName("tr")).get(0).getText()) && subject.equals(item.findElements(By.tagName("tr")).get(1).getText()) && content.equals(item.findElements(By.tagName("tr")).get(2).getText())) {
                found = true;
            }
        }
        assertEquals(false, found);
    }

    @And("^Clicking \"([^\"]*)\"$")
    public void clicking(String button) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        WebElement listing = driver.findElement(By.className("listing"));
        List<WebElement> items = listing.findElements(By.tagName("tr"));
        WebElement item = items.get(items.size() - 1);
        if(button.equals("Edit")) {
            item.findElement(By.id("editButton")).click();
        } else if(button.equals("Delete")) {
            item.findElement(By.id("deleteButton")).click();
        }
    }

    @Then("^The item shouldn't exist anymore$")
    public void theItemShouldnTExistAnymore() {
        driver.get("http://localhost:8080/getEmails");
        // Write code here that turns the phrase above into concrete actions
        WebElement listing = driver.findElement(By.className("listing"));
        List<WebElement> items = listing.findElements(By.tagName("tr"));
        WebElement item = items.get(items.size() - 1);
    }

    @Then("^The service should not accept the new action$")
    public void theServiceShouldNotAcceptTheNewAction() {
        assertEquals("http://localhost:8080/getEmails", driver.getCurrentUrl());
    }

    @And("^Editing email with recipient \"([^\"]*)\", subject \"([^\"]*)\", content \"([^\"]*)\" and click \"([^\"]*)\"$")
    public void editingEmailWithRecipientSubjectContentAndClick(String recipient, String subject, String content, String button) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        driver.findElement(By.id("adressfield")).sendKeys(recipient);
        driver.findElement(By.name("subject")).sendKeys(subject);
        driver.findElement(By.name("content")).sendKeys(content);
        if(button.equals("Save")) {
            driver.findElement(By.id("submitButton")).click();
        } else if(button.equals("Cancel")) {
            driver.findElement(By.id("cancelButton")).click();
        }
    }

    @And("^Click \"([^\"]*)\" on the modal$")
    public void clickOnTheModal(String button) throws Throwable {
        if(button.equals("Confirm")) {
            driver.findElement(By.id("deleteButtonModal")).click();
        } else {
            driver.findElement(By.id("closeButtonModal")).click();
        }

    }
}
