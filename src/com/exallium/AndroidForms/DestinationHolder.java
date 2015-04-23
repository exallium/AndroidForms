package com.exallium.AndroidForms;

/**
 * Represents where the data is going to.  Could be a model entity, json object, etc.
 * @param <D> The type of destination
 */
public abstract class DestinationHolder<D> {

    private D destination;

    public DestinationHolder(D destination) {
        this.destination = destination;
    }

    public D getDestination() {
        return destination;
    }

    /**
     * Called when save is requested
     * @param destination The destination to save
     */
    protected abstract void onSave(D destination);

    /**
     * Called lazily when first needed if destination is null
     * @return new instance of D
     */
    protected abstract D onCreate();

    void save() {
        onSave(destination);
    }

    void create() {
        if (destination == null)
            destination = onCreate();
    }
}
