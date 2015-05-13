/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Alex Hart
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
