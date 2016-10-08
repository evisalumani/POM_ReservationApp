package de.tum.pom16.teamtba.reservationapp.utilities;

import android.widget.EditText;

/**
 * Created by evisa on 10/8/16.
 */
public class PhoneValidator extends TextValidator {
    public PhoneValidator(EditText editText, boolean isRequired) {
        super(editText, isRequired);
    }

    @Override
    public void validate(String text) {
        if (!isNumeric(text)) editText.setError(TextValidator.VALID_PHONE);
        super.validate(text);
    }
}
