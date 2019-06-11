package de.digiwill.steps;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import de.digiwill.SpringBootBaseIntegrationTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class AdminOverviewSteps {

    @Autowired
    private SpringBootBaseIntegrationTest springBootBaseIntegrationTest;

    @Then("The admin overview has {int} users")
    public void theAdminOverviewHasUser(int amount) {
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        WebElement listing = driver.findElement(By.className("listing"));
        List<WebElement> items = listing.findElements(By.tagName("tr"));
        assertEquals(amount, items.size() - 1);
    }

    @And("A user with email {string}, first name {string} and surname {string} is displayed")
    public void aUserWithEmailFirstNameAndSurnameIsDisplayed(String email, String firstName, String surName) {
        String fullName = firstName + " " + surName;
        assertTrue(hasUserWithNameAndEmail(fullName, email, "ROLE_USER"));
    }

    @And("No user with email {string}, first name {string} and surname {string} is displayed")
    public void noUserWithEmailFirstNameAndSurnameIsDisplayed(String email, String firstName, String surName) {
        String fullName = firstName + " " + surName;
        assertFalse(hasUserWithNameAndEmail(fullName, email, "ROLE_USER"));
    }

    @And("An admin with email {string}, first name {string} and surname {string} is displayed")
    public void anAdminWithEmailFirstNameAndSurnameIsDisplayed(String email, String firstName, String surName) {
        String fullName = firstName + " " + surName;
        assertTrue(hasUserWithNameAndEmail(fullName, email, "ROLE_USER ROLE_ADMIN"));
    }

    @And("I click table column {int} for user with email {string}")
    public void iClickTableColumnForUserWithEmail(int column, String email) {
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        WebElement listing = driver.findElement(By.className("listing"));
        List<WebElement> items = listing.findElements(By.tagName("tr"));
        boolean foundItem = false;
        for (int i = 1; i < items.size();i++) {
            List<WebElement> fields = items.get(i).findElements(By.tagName("td"));
           if(email.equals(fields.get(1).getText())){
               foundItem = true;
               fields.get(column).findElement(By.className("button")).click();
           }
        }
        assertTrue(foundItem);
    }

    private boolean hasUserWithNameAndEmail(String fullName, String email, String role){
        WebDriver driver = springBootBaseIntegrationTest.getWebDriver();
        WebElement listing = driver.findElement(By.className("listing"));
        List<WebElement> items = listing.findElements(By.tagName("tr"));
        boolean foundItem = false;
        for (int i = 1; i < items.size();i++) {
            List<WebElement> fields = items.get(i).findElements(By.tagName("td"));
            boolean isCorrect = fullName.equals(fields.get(0).getText()) &&
                    email.equals(fields.get(1).getText()) &&
                    role.equals(fields.get(5).getText());

            foundItem |= isCorrect;
        }
        return foundItem;
    }



}
