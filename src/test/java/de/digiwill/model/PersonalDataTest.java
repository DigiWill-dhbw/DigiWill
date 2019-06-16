package de.digiwill.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class PersonalDataTest {

    @Test
    public void getFullNameReversed() {
        PersonalData personalData = new PersonalData("Max", "Mustermann", new Date(2000, 1, 1), Address.getInitial());
        Assert.assertEquals("Mustermann, Max", personalData.getFullNameReversed());
    }


}
