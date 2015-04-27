package com.exallium.AndroidForms.validators;

import java.lang.ref.WeakReference;

/**
 * Generic abstract validator for field validation.  Should not
 * store any state information.  Validating the same field with the
 * same data should return the same result every time.
 *
 * A field should be an Object, not a primitive, and should be one which does not
 * change it's reference.  Not doing so will cause improper behavior.  A good example
 * of this is an EditText widget
 *
 * @param <F> The field type we are validating.
 */
public abstract class Validator<F> {

    private final WeakReference<F> weakField;

    /**
     * Create the validator for the specified field
     * @param field The field this instance will validate
     */
    public Validator(F field) {
        weakField = new WeakReference<F>(field);
    }

    /**
     * Validates the field
     * @return true if the field is non-null and onValidate is true else false
     */
    public final boolean isValid() {
        F field = weakField.get();
        return field != null && onValidate(field);

    }

    /**
     * Custom validation method for a given field
     * @param field The field we are validating (handed in via isValid)
     * @return Whether validation was successful.
     */
    protected abstract boolean onValidate(F field);
}
