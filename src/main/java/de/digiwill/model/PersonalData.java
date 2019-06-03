package de.digiwill.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;

import java.util.Date;

@TypeAlias("personalData")
public class PersonalData {
    private String firstName;
    private String surname;
    private Date dateOfBirth;
    private Address address;

    @PersistenceConstructor
    public PersonalData(String firstName, String surname, Date dateOfBirth, Address address) {
        this.firstName = firstName;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public static PersonalData getInitial(){
        return new PersonalData("", "", null, Address.getInitial());
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

    public Address getAddress() {
        return address;
    }
}
