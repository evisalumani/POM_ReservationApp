package de.tum.pom16.teamtba.reservationapp.utilities;

import android.content.Context;
import android.widget.EditText;

import de.tum.pom16.teamtba.reservationapp.activities.ReservationDetailsActivity;

/**
 * Created by evisa on 10/8/16.
 */
public class EmailValidator extends TextValidator {
    public EmailValidator(Context callingActivity, EditText editText, boolean isRequired) {
        super(callingActivity, editText, isRequired);
    }

    @Override
    public boolean validate(String text) {
        //simple regex email validator
        //source: http://stackoverflow.com/questions/201323/using-a-regular-expression-to-validate-an-email-address
        //field is required (+ means 1 or more character)
        isValid = text.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
        if (!isValid)
            editText.setError(TextValidator.VALID_EMAIL);

        return isValid;
    }

    @Override
    protected void setInput(String input) {
        ((ReservationDetailsActivity)callingActivity).setEmail(input);
    }
}
