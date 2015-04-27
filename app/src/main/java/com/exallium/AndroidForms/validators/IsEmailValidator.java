package com.exallium.AndroidForms.validators;

import java.util.regex.Pattern;

public abstract class IsEmailValidator<F> extends Validator<F> {

    Pattern emailPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public IsEmailValidator(F field) {
        super(field);
    }

    @Override
    protected boolean onValidate(F field) {
        return emailPattern.matcher(getString(field)).matches();
    }

    protected abstract String getString(F field);

}
