package de.digiwill.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.digiwill.SpringBootBaseIntegrationTest;
import de.digiwill.exception.EmailException;
import de.digiwill.model.EmailAction;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.validation.constraints.Email;
import java.util.List;

import static org.junit.Assert.*;

@Ignore
public class SetupCustomEmailsFeatureTest extends SpringBootBaseIntegrationTest {

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
        if ("Save".equals(button)) {
            driver.findElement(By.id("submitButton")).click();
        } else if ("Cancel".equals(button)) {
            driver.findElement(By.id("cancelButton")).click();
        }
    }

    @Then("^The service should accept the new action$")
    public void theServiceShouldAcceptTheNewAction() {
        assertEquals("http://localhost:" + getPort() + "/getEmails", getWebDriver().getCurrentUrl());
    }

    @And("^A new item with recipient \"([^\"]*)\", subject \"([^\"]*)\", content \"([^\"]*)\" should exist$")
    public void aNewItemWithRecipientSubjectContentShouldExist(String recipient, String subject, String content) throws EmailException {
        boolean foundItem = hasItem(recipient, subject, content);
        assertTrue("An item which should have existed wasn't found", foundItem);
    }

    @And("^No item with recipient \"([^\"]*)\", subject \"([^\"]*)\", content \"([^\"]*)\" should exist$")
    public void noItemWithRecipientSubjectContentShouldExist(String recipient, String subject, String content) throws EmailException {
        boolean foundItem = hasItem(recipient, subject, content);
        assertFalse("An item which shouldn't have existed was found", foundItem);
    }

    private boolean hasItem(String recipient, String subject, String content) throws EmailException {
        WebDriver driver = getWebDriver();
        driver.get("http://localhost:" + getPort() + "/getEmails");
        WebElement listing = driver.findElement(By.className("listing"));
        List<WebElement> items = listing.findElements(By.tagName("tr"));
        items.remove(0);
        boolean foundItem = false;
        for (WebElement item : items) {
            boolean isCorrect = recipient.equals(item.findElements(By.tagName("td")).get(0).getText()) &&
                    subject.equals(item.findElements(By.tagName("td")).get(1).getText()) &&
                    skippedContent(content).equals(item.findElements(By.tagName("td")).get(2).getText());

            foundItem |= isCorrect;
        }
        return foundItem;
    }


    @And("^Clicking \"([^\"]*)\"$")
    public void clicking(String button) {
        WebDriver driver = getWebDriver();
        // Write code here that turns the phrase above into concrete actions
        WebElement listing = driver.findElement(By.className("listing"));
        List<WebElement> items = listing.findElements(By.tagName("tr"));
        WebElement item = items.get(items.size() - 1);
        if ("Edit".equals(button)) {
            item.findElement(By.id("editButton")).click();
        } else if ("Delete".equals(button)) {
            item.findElement(By.id("deleteButton")).click();
        } else if ("logout".equalsIgnoreCase(button)) {
            driver.findElement(By.className("myDropdown"));
            listing.findElements(By.tagName("input"));
            items.get(items.size());
            if ("Logout".equals(button)) {
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
        items.get(items.size() - 1);
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
        if ("Save".equals(button)) {
            driver.findElement(By.id("saveButton")).click();
        } else if ("Cancel".equals(button)) {
            driver.findElement(By.id("cancelButton")).click();
        }
    }

    @And("^Click \"([^\"]*)\" on the modal$")
    public void clickOnTheModal(String button) {
        WebDriver driver = getWebDriver();
        if ("Confirm".equals(button)) {
            driver.findElement(By.id("confirmDeleteButton")).click();
        } else {
            driver.findElement(By.id("cancelDeleteButton")).click();
        }

    }

    public String skippedContent(String content) throws EmailException {
        EmailAction emailAction = EmailAction.generateEmailAction(null, null, content, false);
        return emailAction.getSkipedContent();
    }
}
