package de.digiwill.model;

public class Address {
    private final String zipCode;
    private final String city;
    private final String streetAddress;
    private final String country;

    public Address(String zipCode, String city, String streetAddress, String country) {
        this.zipCode = zipCode;
        this.city = city;
        this.streetAddress = streetAddress;
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        if(streetAddress == null && zipCode == null && city == null && country == null){
            return "No address available";
        }
        return streetAddress + "\n" + zipCode + " " + city + "\n" + "\n" + country;
    }
}
