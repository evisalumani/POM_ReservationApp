package de.tum.pom16.teamtba.reservationapp.utilities;

import android.content.Context;
import android.widget.EditText;

import de.tum.pom16.teamtba.reservationapp.activities.ReservationDetailsActivity;

/**
 * Created by evisa on 10/8/16.
 */
public class PhoneValidator extends TextValidator {
    public PhoneValidator(Context callingActivity, EditText editText, boolean isRequired) {
        super(callingActivity, editText, isRequired);
    }

    @Override
    public boolean validate(String text) {
        //field is required (not null or empty)
        isValid = isNumeric(text) && !isNullOrEmpty(text);
        if (!isValid) editText.setError(TextValidator.VALID_PHONE);
        return isValid;
    }

    @Override
    protected void setInput(String input) {
        ((ReservationDetailsActivity)callingActivity).setPhone(input);
    }
}