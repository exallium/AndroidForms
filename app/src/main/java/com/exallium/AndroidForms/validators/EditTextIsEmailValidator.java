package com.exallium.AndroidForms.validators;

import android.widget.EditText;

public class EditTextIsEmailValidator extends IsEmailValidator<EditText> {
    public EditTextIsEmailValidator(EditText field) {
        super(field);
    }

    @Override
    protected String getString(EditText field) {
        return field.getText().toString();
    }
}
