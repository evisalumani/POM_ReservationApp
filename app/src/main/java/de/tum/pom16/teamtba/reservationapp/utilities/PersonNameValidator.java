package de.tum.pom16.teamtba.reservationapp.utilities;

import android.widget.EditText;

/**
 * Created by evisa on 10/8/16.
 */
public class PersonNameValidator extends TextValidator {
    public PersonNameValidator(EditText editText, boolean isRequired) {
        super(editText, isRequired);
    }

    @Override
    public void validate(String text) {
        //capital/small letters and space allowed
        //min length of 2 characters
        if (!text.matches("[a-zA-Z\\s]+") || text.length() < 2) editText.setError(TextValidator.VALID_NAME);
        super.validate(text);
    }
}
