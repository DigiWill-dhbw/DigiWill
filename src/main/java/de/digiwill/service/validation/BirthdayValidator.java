package de.digiwill.service.validation;

import org.springframework.util.MultiValueMap;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BirthdayValidator extends Validator {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final int minimumAge = 13;

    public BirthdayValidator() {
        super(ValidationResponse.BIRTHDAY_INVALID);
    }

    @Override
    public boolean validate(final MultiValueMap<String, String> formData) {
        Date birthdayDate;
        try {
            birthdayDate = DATE_FORMAT.parse(formData.getFirst("birthday"));
            if (!isOldEnough(birthdayDate)) {
                setResponse(ValidationResponse.TO_YOUNG);
                return false;
            }
        } catch (ParseException e) {
            setResponse(ValidationResponse.BIRTHDAY_INVALID);
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
        today.add(Calendar.YEAR, -minimumAge);
        return today.getTime().compareTo(date) >= 0;
    }
}
