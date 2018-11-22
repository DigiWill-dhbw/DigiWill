package digiwill;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("data")
public class PersonalData {
    private String firstName;
    private String surname;
    private long dateOfBirth;
    private String zipCode;
    private String city;
    private String streetAddress;
    private String country;

    @PersistenceConstructor
    public PersonalData(String firstName, String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }

    @PersistenceConstructor
    public PersonalData(String firstName, String surname, long dateOfBirth, String zipCode, String city, String streetAddress, String country) {
        this(firstName, surname);
        this.dateOfBirth = dateOfBirth;
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

    public long getDateOfBirth() {
        return dateOfBirth;
    }
}
