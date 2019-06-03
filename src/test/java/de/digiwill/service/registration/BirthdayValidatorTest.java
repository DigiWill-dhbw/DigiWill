package de.digiwill.service.registration;

import de.digiwill.service.validation.BirthdayValidator;
import de.digiwill.service.validation.ValidationResponse;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.*;

public class BirthdayValidatorTest {
    private BirthdayValidator birthdayValidator;


    @Before
    public void setUp() {
        birthdayValidator = new BirthdayValidator();
    }

    @Test
    public void validDate() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("birthday", "1990-01-01");
        assertTrue(birthdayValidator.validate(formData));
    }

    @Test
    public void tooYoung() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("birthday", "2019-01-01");
        assertFalse(birthdayValidator.validate(formData));
        assertEquals(ValidationResponse.TO_YOUNG, birthdayValidator.getResponse());
    }

    @Test
    public void wrongFormat() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("birthday", "1990-aa-60");
        assertFalse(birthdayValidator.validate(formData));
        assertEquals(ValidationResponse.BIRTHDAY_INVALID, birthdayValidator.getResponse());
    }
}