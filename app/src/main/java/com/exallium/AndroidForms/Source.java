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

package com.exallium.AndroidForms;

import com.exallium.AndroidForms.validators.Validator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents where the form data is coming from.  Could be a view, json object, or whatever.
 * @param <S> The type of source
 */
public abstract class Source<S> {
    private S source;
    private List<Validator> validators = new LinkedList<Validator>();

    public Source(S source) {
        this.source = source;
        if (source != null)
            addValidators(onSourceCreated());
    }

    private void addValidators(List<? extends Validator>validators) {
        this.validators.clear();
        this.validators.addAll(validators);
    }

    final boolean onValidate() {
        if (source == null)
            return false;

        for (Validator validator : validators)
            if (!validator.isValid())
                return false;

        return true;
    }

    /**
     * Creates the source if it is null and returns it
     * @return the current source
     */
    public S getSource() {
        if (source == null) {
            source = onCreate();
            addValidators(onSourceCreated());
        }
        return source;
    }

    /**
     * Called lazily when first needed if Source is null
     * @return The new instance
     */
    protected abstract S onCreate();

    /**
     * Your source has been created... add some validators!
     */
    protected abstract List<? extends Validator> onSourceCreated();

}
