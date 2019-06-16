package de.digiwill.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WebhookSteps {
    @Autowired
    WebDriver webDriver;

    @And("Webhook is not configured")
    public void webhookIsNotConfigured() {
        assertTrue(webDriver.findElement(By.id("apikey")).getText().isEmpty());
        assertTrue(webDriver.findElements(By.tagName("td")).isEmpty());

    }

    @And("I enter event {string} at position {int}")
    public void iEnterEvent(String eventName, int position) {
        List<WebElement> webElementList = webDriver.findElements(By.tagName("tr"));
        webElementList.get(position).findElement(By.tagName("input")).sendKeys(eventName);
    }

    @And("I {string} see event {string} in table")
    public void iSeeEventInTable(String doOrDont, String eventName){
        List<WebElement> webElementList;
        boolean found = false;
        for(int tryNumber = 0; tryNumber < 3; tryNumber++) {
            try {
                webElementList = webDriver.findElement(By.id("eventList")).findElements(By.tagName("tr"));
                for (WebElement webElement : webElementList) {
                    if (webElement.findElement(By.className("input-fields")).getAttribute("value").contentEquals(eventName)) {
                        found = true;
                    }
                }
            } catch (StaleElementReferenceException ex) {
                webElementList = webDriver.findElement(By.id("eventList")).findElements(By.tagName("tr"));
            }
        }

        boolean isThere = true;
        if(doOrDont.contentEquals("do not")){
            isThere = false;
        }

        assertEquals("Item "+eventName+ " not found on eventlist.", isThere , found);
    }


    @And("I see {string} in input field with id {string}")
    public void iSeeInFieldWithId(String input, String id) {
        WebElement webElement = webDriver.findElement(By.id(id));

        boolean equalContent = false;
        for(int tryNumber = 0; tryNumber < 3; tryNumber++) {
            try {
                    equalContent = webElement.getAttribute("value").contentEquals(input);
            } catch (StaleElementReferenceException ex) {
                webElement = webDriver.findElement(By.id(id));
            }
        }
        assertTrue("Content of input field with id "+id+" is not equal to "+input ,equalContent);
    }

    @Then("I clear event at position {int}")
    public void iClearEventAtPosition(int position) {
        List<WebElement> webElementList = webDriver.findElements(By.tagName("tr"));
        webElementList.get(position).findElement(By.tagName("input")).clear();
    }
}