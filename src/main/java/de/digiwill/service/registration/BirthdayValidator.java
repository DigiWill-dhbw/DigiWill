package de.digiwill.service.registration;

import org.springframework.util.MultiValueMap;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class BirthdayValidator extends RegistrationValidator {
    private static final int MINIMUM_AGE = 13;
    private DateFormat dateFormat;

    public BirthdayValidator(DateFormat dateFormat) {
        super(RegistrationResponse.BIRTHDAY_INVALID);
        this.dateFormat = dateFormat;
    }

    @Override
    public boolean validate(final MultiValueMap<String, String> formData) {
        Date birthdayDate;
        try {
            birthdayDate = dateFormat.parse(formData.getFirst("birthday"));
            if (!isOldEnough(birthdayDate)) {
                setResponse(RegistrationResponse.TO_YOUNG);
                return false;
            }
        } catch (ParseException e) {
            setResponse(RegistrationResponse.BIRTHDAY_INVALID);
            return false;
        }
        return true;
    }


    private boolean isOldEnough(Date date) {
        //TODO replace with DateTime implementation
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.add(Calendar.YEAR, -MINIMUM_AGE);
        return today.getTime().compareTo(date) >= 0;
    }
}
