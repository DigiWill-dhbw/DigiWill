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

import static org.junit.Assert.assertEquals;

public class sendLifeSign {
    @Autowired
    SpringBootBaseIntegrationTest springBootBaseIntegrationTest;
    WebDriver driver;

    @When("^Clicking in header \"([^\"]*)\" on Mainpage$")
    public void clickInHeader(String buttonName) {
        driver = springBootBaseIntegrationTest.getWebDriver();
        // Write code here that turns the phrase above into concrete actions
        driver.get("http://localhost:" + springBootBaseIntegrationTest.getPort() + "/");

        WebElement listing = driver.findElement(By.className("dropdown-content"));

        if(buttonName.equalsIgnoreCase("logout")){
            List<WebElement> items = listing.findElements(By.tagName("input"));
            WebElement item = items.get(items.size()-1);
            assertEquals(buttonName,item.getAttribute("value"));
            item.submit();

        }else if(buttonName.equalsIgnoreCase("send life sign")){

            List<WebElement> items = listing.findElements(By.tagName("input"));
            WebElement item = items.get(items.size()-3);
            assertEquals(buttonName, item.getAttribute("value"));
            item.submit();
        }else
        {
            assertEquals(0,1);
        }
    }

    @Then("^Life sign was sent$")
    public void lifeSignWasSent() {
        UserHandle userHandle = springBootBaseIntegrationTest.getUserHandleManager().loadUserByUserName("test_user_send_life_sign@digiwill.de");
        Assert.assertTrue(userHandle.getLastSignOfLife() != 0);
    }


}
