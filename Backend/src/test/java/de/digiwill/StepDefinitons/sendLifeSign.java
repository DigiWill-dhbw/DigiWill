package de.digiwill.StepDefinitons;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.digiwill.SpringBootBaseIntegrationTest;
import de.digiwill.model.UserHandle;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class sendLifeSign {
    @Autowired
    SpringBootBaseIntegrationTest springBootBaseIntegrationTest;
    WebDriver driver;

    @When("^Clicking in header \"([^\"]*)\"$")
    public void clickInHeader(String button) {
        driver = springBootBaseIntegrationTest.getWebDriver();
        // Write code here that turns the phrase above into concrete actions
        WebElement listing = driver.findElement(By.className("myDropdown"));
        List<WebElement> items = listing.findElements(By.tagName("input"));
        WebElement item = items.get(items.size() - 1);
        if (button.equals("Send life sign")) {
            item.findElement(By.id("sendLifeSign")).click();
        }
    }

    @Then("^Life sign was sent$")
    public void lifeSignWasSent() {
        UserHandle userHandle = springBootBaseIntegrationTest.getUserHandleManager().loadUserByUserName("test_user_send_life_sign@digiwill.de");
        Assert.assertTrue(userHandle.getLastSignOfLife() != 0);
    }


}
