package de.tum.pom16.teamtba.reservationapp.utilities;

import android.content.Context;
import android.text.TextUtils;
import android.widget.EditText;

import de.tum.pom16.teamtba.reservationapp.activities.ReservationDetailsActivity;

/**
 * Created by evisa on 10/8/16.
 */
public class AlphaNumericTextValidator extends TextValidator {
    public AlphaNumericTextValidator(Context callingActivity, EditText editText, boolean isRequired) {
        super(callingActivity, editText, isRequired);
    }

    @Override
    protected boolean validate(String text) {
        //allow only alpha-numericand basic punctuation characters
        //field is optional (0 or more characters)
        isValid = text.matches("^[a-zA-Z0-9.,?\\s]*$");
        if (!isValid)
            editText.setError(TextValidator.VALID_CHARACTERS);

        return isValid;
    }

    @Override
    protected void setInput(String input) {
        ((ReservationDetailsActivity)callingActivity).setSpecialRequests(input);
    }
}
