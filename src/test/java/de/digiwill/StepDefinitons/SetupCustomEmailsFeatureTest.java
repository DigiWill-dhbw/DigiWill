package de.digiwill.StepDefinitons;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.digiwill.SpringBootBaseIntegrationTest;
import de.digiwill.model.EmailAction;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertEquals;

@Ignore
public class SetupCustomEmailsFeatureTest extends SpringBootBaseIntegrationTest {

   /* @And("^The user \"([^\"]*)\" with the password \"([^\"]*)\" is logged in$")
    public void theUserWithThePasswordIsLoggedInAndOnTheActionOverviewPage(String email, String password) {
        setUpSingleUser(email, password);
        driver = getWebDriver();
        driver.get("http://localhost:" + getPort() + "/");
        driver.findElement(By.id("loginButtonHeader")).click();
        driver.findElement(By.id("emailInput")).sendKeys(email);
        driver.findElement(By.id("passwordInput")).sendKeys(password);
        driver.findElement(By.id("loginButton")).click();
    }*/

    @And("^The user is on the actions overview page$")
    public void theUserIsOnActionsOverviewPage() {
        getWebDriver().get("http://localhost:" + getPort() + "/getEmails");
    }


    @And("^There are no Email Actions$")
    public void thereAreNoEmailActions() {
//TODO Implement
    }

    @When("^Create new email action with recipient \"([^\"]*)\", subject \"([^\"]*)\", content \"([^\"]*)\" and click \"([^\"]*)\"$")
    public void createNewEmailActionWithRecipientSubjectContentAndClick(String recipient, String subject, String content, String button) {
        // Write code here that turns the phrase above into concrete actions
        WebDriver driver = getWebDriver();
        driver.get("http://localhost:" + getPort() + "/addEmail");
        driver.findElement(By.id("addressField")).sendKeys(recipient);
        driver.findElement(By.name("subject")).sendKeys(subject);
        driver.findElement(By.name("content")).sendKeys(content);
        if (button.equals("Save")) {
            driver.findElement(By.id("submitButton")).click();
        } else if (button.equals("Cancel")) {
            driver.findElement(By.id("cancelButton")).click();
        }
    }

    @Then("^The service should accept the new action$")
    public void theServiceShouldAcceptTheNewAction() {
        assertEquals("http://localhost:" + getPort() + "/getEmails", getWebDriver().getCurrentUrl());
    }

    @And("^A new item with recipient \"([^\"]*)\", subject \"([^\"]*)\", content \"([^\"]*)\" should exist$")
    public void aNewItemWithRecipientSubjectContentShouldExist(String recipient, String subject, String content) {
        WebDriver driver = getWebDriver();
        driver.get("http://localhost:" + getPort() + "/getEmails");
        WebElement listing = driver.findElement(By.className("listing"));
        List<WebElement> items = listing.findElements(By.tagName("tr"));
        items.remove(0);
        for (WebElement item :
                items) {
            assertEquals(recipient, item.findElements(By.tagName("td")).get(0).getText());
            assertEquals(subject, item.findElements(By.tagName("td")).get(1).getText());
            assertEquals(skippedContent(content), item.findElements(By.tagName("td")).get(2).getText());
        }
    }

    @And("^No new item with recipient \"([^\"]*)\", subject \"([^\"]*)\", content \"([^\"]*)\" should exist$")
    public void noNewItemWithRecipientSubjectContentShouldExist(String recipient, String subject, String content) {
        // Write code here that turns the phrase above into concrete actions
        WebDriver driver = getWebDriver();
        driver.get("http://localhost:" + getPort() + "/getEmails");
        WebElement listing = driver.findElement(By.className("listing"));
        List<WebElement> items = listing.findElements(By.tagName("tr"));
        items.remove(0);
        for (WebElement item :
                items) {
            assertEquals(recipient, item.findElements(By.tagName("td")).get(0).getText());
            assertEquals(subject, item.findElements(By.tagName("td")).get(1).getText());
            assertEquals(skippedContent(content), item.findElements(By.tagName("td")).get(2).getText());
        }
    }

    @And("^Clicking \"([^\"]*)\"$")
    public void clicking(String button) {
        WebDriver driver = getWebDriver();
        // Write code here that turns the phrase above into concrete actions
        WebElement listing = driver.findElement(By.className("listing"));
        List<WebElement> items = listing.findElements(By.tagName("tr"));
        WebElement item = items.get(items.size() - 1);
        if (button.equals("Edit")) {
            item.findElement(By.id("editButton")).click();
        } else if (button.equals("Delete")) {
            item.findElement(By.id("deleteButton")).click();
        } else if (button.equalsIgnoreCase("logout")){
            WebElement dropdown = driver.findElement(By.className("myDropdown"));
            List<WebElement> dropdownTtems = listing.findElements(By.tagName("input"));
            WebElement dropdownItem = items.get(items.size());
            if (button.equals("Logout")) {
                item.findElement(By.id("SendLifeSingFeatureTest")).click();
            }
        }
    }

    @Then("^The item shouldn't exist anymore$")
    public void theItemShouldnTExistAnymore() {
        WebDriver driver = getWebDriver();
        driver.get("http://localhost:" + getPort() + "/getEmails");
        // Write code here that turns the phrase above into concrete actions
        WebElement listing = driver.findElement(By.className("listing"));
        List<WebElement> items = listing.findElements(By.tagName("tr"));
        WebElement item = items.get(items.size() - 1);
    }

    @Then("^The service should not accept the new action$")
    public void theServiceShouldNotAcceptTheNewAction() {
        assertEquals("http://localhost:" + getPort() + "/getEmails", getWebDriver().getCurrentUrl());
    }

    @And("^Editing email with recipient \"([^\"]*)\", subject \"([^\"]*)\", content \"([^\"]*)\" and click \"([^\"]*)\"$")
    public void editingEmailWithRecipientSubjectContentAndClick(String recipient, String subject, String content, String button) {
        WebDriver driver = getWebDriver();
        // Write code here that turns the phrase above into concrete actions
        driver.findElement(By.id("addressField")).clear();
        driver.findElement(By.id("addressField")).sendKeys(recipient);
        driver.findElement(By.name("subject")).clear();
        driver.findElement(By.name("subject")).sendKeys(subject);
        driver.findElement(By.name("content")).clear();
        driver.findElement(By.name("content")).sendKeys(content);
        if (button.equals("Save")) {
            driver.findElement(By.id("saveButton")).click();
        } else if (button.equals("Cancel")) {
            driver.findElement(By.id("cancelButton")).click();
        }
    }

    @And("^Click \"([^\"]*)\" on the modal$")
    public void clickOnTheModal(String button) {
        WebDriver driver = getWebDriver();
        if (button.equals("Confirm")) {
            driver.findElement(By.id("confirmDeleteButton")).click();
        } else {
            driver.findElement(By.id("cancelDeleteButton")).click();
        }

    }

    public String skippedContent(String content) {
        EmailAction ea = new EmailAction(null, false, null, null, false, content);
        return ea.getSkipedContent();
    }
}
