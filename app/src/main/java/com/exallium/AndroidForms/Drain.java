package com.exallium.AndroidForms;

/**
 * Represents where the data is going to.  Could be a model entity, json object, etc.
 * @param <D> The type of drain
 */
public abstract class Drain<D> {

    private D drain;

    public Drain(D drain) {
        this.drain = drain;
    }

    /**
     * Returns the current drain object or NULL if it has not been created yet.
     * @return
     */
    public D getDrain() {
        return drain;
    }

    /**
     * Called when save is requested
     * @param drain The drain to save
     */
    protected abstract void onSave(D drain);

    /**
     * Called lazily when first needed if drain is null
     * @return new instance of D
     */
    protected abstract D onCreate();

    void save() {
        onSave(drain);
    }

    void create() {
        if (drain == null)
            drain = onCreate();
    }
}
