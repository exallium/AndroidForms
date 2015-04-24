package com.exallium.AndroidForms;

import com.exallium.AndroidForms.validators.Validator;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents where the form data is coming from.  Could be a view, json object, or whatever.
 * @param <S> The type of source
 */
public abstract class SourceHolder<S> {
    private S source;
    private List<Validator> validators = new LinkedList<Validator>();

    public SourceHolder(S source) {
        this.source = source;
    }

    public final void addValidator(Validator validator) {
        this.validators.add(validator);
    }

    boolean onValidate() {
        if (source == null)
            return false;

        for (Validator validator : validators)
            if (!validator.isValid())
                return false;

        return true;
    }

    public S getSource() {
        if (source == null)
            source = onCreate();
        return source;
    }

    /**
     * Called lazily when first needed if Source is null
     * You should also add your validators here.
     * @return The new instance
     */
    protected abstract S onCreate();

}
