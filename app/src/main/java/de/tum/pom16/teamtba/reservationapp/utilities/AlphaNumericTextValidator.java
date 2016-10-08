package de.tum.pom16.teamtba.reservationapp.utilities;

import android.text.TextUtils;
import android.widget.EditText;

/**
 * Created by evisa on 10/8/16.
 */
public class AlphaNumericTextValidator extends TextValidator {
    public AlphaNumericTextValidator(EditText editText, boolean isRequired) {
        super(editText, isRequired);
    }

    @Override
    protected void validate(String text) {
        //allow only alpha-numericand basic punctuation characters
        if (!text.matches("[a-zA-Z0-9.,?]*"))
            editText.setError(TextValidator.VALID_CHARACTERS);
        super.validate(text);
    }
}
