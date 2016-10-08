package de.tum.pom16.teamtba.reservationapp.utilities;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by evisa on 10/8/16.
 */
public abstract class TextValidator implements TextWatcher {
    protected final Context callingActivity;
    protected final EditText editText;
    protected final boolean isRequired;
    protected boolean isValid;
    protected final static String REQUIRED_FIELD = "Required";
    protected final static String VALID_NAME = "Please enter a valid name";
    protected final static String VALID_EMAIL = "Please enter a valid email address";
    protected final static String VALID_PHONE = "Please enter a valid phone number";
    protected final static String VALID_CHARACTERS = "Please enter valid characters";

    protected TextValidator(Context callingActivity, EditText editText, boolean isRequired) {
        this.callingActivity = callingActivity;
        this.editText = editText;
        this.isRequired = isRequired;
        isValid = false;
    }

    protected abstract boolean validate(String text);

    protected void setErrorToEditText(String errorStr) {
        if (errorStr != null) editText.setError(errorStr);
    }

    protected boolean isNullOrEmpty(String string){
        return TextUtils.isEmpty(string);
    }

    public boolean isNumeric(String string){
        return TextUtils.isDigitsOnly(string);
    }

    protected abstract void setInput(String input);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = editText.getText().toString().trim();
        //trim both ends of the input before validating
        if (validate(text)) {
            //update data fields in calling activity only if valid
            setInput(text);
        }
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Not important here */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Not important here */ }
}