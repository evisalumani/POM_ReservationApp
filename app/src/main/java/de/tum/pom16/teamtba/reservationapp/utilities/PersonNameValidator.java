package de.tum.pom16.teamtba.reservationapp.utilities;

import android.content.Context;
import android.widget.EditText;

import de.tum.pom16.teamtba.reservationapp.activities.ReservationDetailsActivity;

/**
 * Created by evisa on 10/8/16.
 */
public class PersonNameValidator extends TextValidator {
    public PersonNameValidator(Context callingActivity, EditText editText, boolean isRequired) {
        super(callingActivity, editText, isRequired);
    }

    @Override
    public boolean validate(String text) {
        //capital/small letters and space allowed
        //field is required: min length of 2 characters
        isValid = text.matches("^[a-zA-Z\\s]{2,}$");
        if (!isValid) editText.setError(TextValidator.VALID_NAME);

        return isValid;
    }

    @Override
    protected void setInput(String input) {
        ((ReservationDetailsActivity)callingActivity).setName(editText, input);
    }
}
