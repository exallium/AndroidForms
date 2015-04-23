package com.exallium.AndroidForms;

/**
 * Represents where the form data is coming from.  Could be a view, json object, or whatever.
 * @param <S> The type of source
 */
public abstract class SourceHolder<S> {
    private S source;

    public SourceHolder(S source) {
        this.source = source;
    }

    boolean validate() {
        return isValid(source);
    }

    void create() {
        if (source == null)
            source = onCreate();
    }

    public S getSource() {
        return source;
    }

    /**
     * Called lazily when first needed if Source is null
     * @return The new instance
     */
    protected abstract S onCreate();

    /**
     * Called at time of validation.  Should validate it's fields and return accordingly
     * @return true if valid, false otherwise
     */
    protected abstract boolean isValid(S source);
}
