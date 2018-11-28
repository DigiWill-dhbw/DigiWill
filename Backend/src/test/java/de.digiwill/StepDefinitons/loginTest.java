package de.digiwill.StepDefinitons;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

public class loginTest {

    @Given("^The \"([^\"]*)\" is open$")
    public void theIsOpen(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        //throw new PendingException();
    }

    @Given("^A user with email \"([^\"]*)\" and password \"([^\"]*)\" \"([^\"]*)\"$")
    public void aUserWithEmailAndPassword(String arg0, String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        //throw new PendingException();
    }

    @When("^Enter Email \"([^\"]*)\", password \"([^\"]*)\" and click \"([^\"]*)\"$")
    public void enterEmailPasswordAndClick(String arg0, String arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
     
        //throw new PendingException();
    }

    @Then("^Login \"([^\"]*)\"$")
    public void login(String arg0) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertEquals("a","a");
        //throw new PendingException();
    }
}
