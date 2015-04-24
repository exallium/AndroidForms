package com.exallium.AndroidForms.validators;

import android.widget.EditText;

public class EditTextNotEmptyValidator extends Validator<EditText> {
    public EditTextNotEmptyValidator(EditText field) {
        super(field);
    }

    @Override
    protected boolean onValidate(EditText field) {
        return field.length() != 0;
    }
}
