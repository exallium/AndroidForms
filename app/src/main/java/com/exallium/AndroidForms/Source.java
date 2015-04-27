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
