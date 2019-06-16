package de.digiwill.model;

import org.junit.Assert;
import org.junit.Test;

public class AddressTest {

    @Test
    public void toStringTest() {
        Address address = new Address("12345", "Test-City", "Test-Street 1", "Test-Country");
        Assert.assertEquals("Test-Street 1\n12345 Test-City\n\nTest-Country", address.toString());
    }

}
