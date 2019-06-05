package de.digiwill.steps;

import cucumber.api.java.en.Given;
import de.digiwill.SpringBootBaseIntegrationTest;
import de.digiwill.model.Address;
import de.digiwill.model.PersonalData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UserCreationSteps {

    @Autowired
    private SpringBootBaseIntegrationTest springBootBaseIntegrationTest;

    @Given("^A user with email \"([^\"]*)\" and password \"([^\"]*)\" exists$")
    public void aUserWithEmailAndPasswordExists(String email, String password){
        springBootBaseIntegrationTest.setUpUser(email, password);
    }

    @Given("^An admin with email \"([^\"]*)\" and password \"([^\"]*)\" exists$")
    public void anAdminWithEmailAndPasswordExists(String email, String password){
        springBootBaseIntegrationTest.setUpAdmin(email, password);
    }

    @Given("A user with email {string}, password {string}, first name {string} and surname {string} exists")
    public void aUserWithEmailPasswordFirstNameAndSurnameExists(String email, String password, String firstName, String surName) {
        PersonalData personalData = new PersonalData(firstName, surName, new GregorianCalendar(1970, Calendar.JANUARY, 1).getTime(), Address.getInitial());
        springBootBaseIntegrationTest.setUpUser(email, password, personalData);
    }
}
