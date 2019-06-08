package de.digiwill.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.digiwill.SpringBootBaseIntegrationTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GenericSteps {
    @Autowired
    private SpringBootBaseIntegrationTest springBootBaseIntegrationTest;

    @When("I enter {string} into field with id {string}")
    public void iEnterIntoFieldWithId(String text, String id) {
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        driver.findElement(By.id(id)).sendKeys(text);
    }

    @And("I clear field with id {string}")
    public void iClearFieldWithId(String id) {
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        driver.findElement(By.id(id)).clear();
    }

    @And("I click on element with id {string}")
    public void iClickOnElementWithId(String id) {
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        WebElement element = driver.findElement(By.id(id));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }

    @Then("I'm on page {string}")
    public void iMOnPage(String url) {
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        assertEquals("http://localhost:" + springBootBaseIntegrationTest.getPort() + url, driver.getCurrentUrl());
    }

    @Then("I'm on page with title {string}")
    public void iMOnPageWithTitle(String title) {
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        assertEquals(title, driver.getTitle());
    }

    @Then("I'm on page with url containing {string}")
    public void iMOnPageWithUrlContaining(String urlPart) {
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        assertTrue("User is on wrong page: " + driver.getCurrentUrl() + "\n" +
                "should contain: " + urlPart, driver.getCurrentUrl().contains(urlPart));
    }
}
