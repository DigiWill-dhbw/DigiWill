package de.digiwill.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import de.digiwill.SpringBootBaseIntegrationTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class GenericSteps {
    @Autowired
    private SpringBootBaseIntegrationTest springBootBaseIntegrationTest;

    @When("I enter {string} into field with id {string}")
    public void iEnterIntoFieldWithId(String text, String id) {
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        driver.findElement(By.id(id)).sendKeys(text);
    }

    @And("I click on element with id {string}")
    public void iClickOnElementWithId(String id) {
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        driver.findElement(By.id(id)).click();
    }

    @Then("I'm on page {string}")
    public void iMOnPage(String url) {
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        assertEquals("http://localhost:" + springBootBaseIntegrationTest.getPort() + url, driver.getCurrentUrl());
    }
}
