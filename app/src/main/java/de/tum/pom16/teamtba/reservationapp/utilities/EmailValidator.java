package de.tum.pom16.teamtba.reservationapp.utilities;

import android.widget.EditText;

/**
 * Created by evisa on 10/8/16.
 */
public class EmailValidator extends TextValidator {
    public EmailValidator(EditText editText, boolean isRequired) {
        super(editText, isRequired);
    }

    @Override
    public void validate(String text) {
        //simple regex email validator
        //source: http://stackoverflow.com/questions/201323/using-a-regular-expression-to-validate-an-email-address
        if (!text.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"))
            editText.setError(TextValidator.VALID_EMAIL);

        super.validate(text);
    }
}
