package com.exallium.AndroidForms.validators;

import java.lang.ref.WeakReference;

public abstract class Validator<F> {

    private final WeakReference<F> weakField;

    public Validator(F field) {
        weakField = new WeakReference<F>(field);
    }

    public final boolean isValid() {
        F field = weakField.get();
        return field != null && onValidate(field);

    }

    protected abstract boolean onValidate(F field);
}
