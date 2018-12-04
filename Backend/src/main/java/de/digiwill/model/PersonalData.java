package de.digiwill.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;

import java.util.Date;

@TypeAlias("personalData")
public class PersonalData {
    private String firstName;
    private String surname;
    private Date dateOfBirth;
    private String zipCode;
    private String city;
    private String streetAddress;
    private String country;

    @PersistenceConstructor
    public PersonalData(String firstName, String surname, Date dateOfBirth) {
        this.firstName = firstName;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    @PersistenceConstructor
    public PersonalData(String firstName, String surname, Date dateOfBirth, String zipCode, String city, String streetAddress, String country) {
        this(firstName, surname, dateOfBirth);
        this.zipCode = zipCode;
        this.city = city;
        this.streetAddress = streetAddress;
        this.country = country;
    }

    public String getAddress() {
        return streetAddress + "\n" + zipCode + " " + city + "\n" + "\n" + country;
    }

    public String getFullName() {
        return firstName + " " + surname;
    }

    public String getFullNameReversed() {
        return surname + ", " + firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
}
