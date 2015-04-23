package com.exallium.AndroidForms;

import android.os.Bundle;

public abstract class Form<S, D, SH extends Form.SourceHolder<S>, DH extends Form.DestinationHolder<D>> {

    public static abstract class Builder<F extends Form, S, D> {
        private S source;
        private D destination;

        public Builder from(S source) {
            this.source = source;
            return this;
        }

        public Builder to(D destination) {
            this.destination = destination;
            return this;
        }

        /**
         * Simply create an instance of F, passing this, and return it.
         * Thanks Java Generics
         * @return The new instance.
         */
        protected abstract F create();

        public Form build() {
            F form = create();
            form.init();
            return form;
        }

    }

    /**
     * Represents where the form data is coming from.  Could be a view, json object, or whatever.
     * @param <S> The type of source
     */
    public static abstract class SourceHolder<S> {
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

    /**
     * Represents where the data is going to.  Could be a model entity, json object, etc.
     * @param <D> The type of destination
     */
    public static abstract class DestinationHolder<D> {

        private D destination;

        public DestinationHolder(D destination) {
            this.destination = destination;
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

    private final SH sourceHolder;
    private final DH destinationHolder;

    /**
     * Creates a form
     * @param source The source to use, or NULL if SH should create it's own.
     * @param destination The destination to use, or NULL if DH should create it's own.
     */
    protected Form(Builder<? extends Form, S, D> builder) {
        sourceHolder = createSourceHolder(builder.source);
        destinationHolder = createDestinationHolder(builder.destination);
    }

    protected final void init() {
        sourceHolder.create();
        destinationHolder.create();
    }

    /**
     * Populates the source with information from the destination, and then
     * adds information form the given bundle if there is one.
     * @param bundle The bundle of extra data
     */
    public final void display(Bundle bundle) {
        populateSource(sourceHolder, destinationHolder);
        if (bundle != null) populateSourceFromBundle(sourceHolder, bundle);
    }

    /**
     * See display(bundle)
     */
    public final void display() {
        display(null);
    }

    /**
     * See save(bundle)
     */
    public final void save() {
        save(null);
    }

    /**
     * see forceSave(bundle)
     */
    public final void forceSave() {
        forceSave(null);
    }

    /**
     * Populates the destination with information from the source and the given
     * bundle.
     * @param bundle The bundle of extra data
     * @return whether or not data validation was successful
     */
    public final boolean save(Bundle bundle) {
        boolean isValid = isValid();
        if (isValid) {
            forceSave(bundle);
        }
        return isValid;
    }

    /**
     * Saves ignoring validation. Applies bundle if present
     * @param bundle The bundle of extra data
     */
    public final void forceSave(Bundle bundle) {
        populateDestination(sourceHolder, destinationHolder);
        if (bundle != null) populateDestinationFromBundle(destinationHolder, bundle);
        destinationHolder.save();
    }

    /**
     * Returns true on successful validation
     * @return
     */
    public boolean isValid() {
        return sourceHolder.validate();
    }

    /**
     * Should create a new instance of SH and return it.
     * @param source The source to base it off of, or NULL
     * @return A new instance of SH
     */
    protected abstract SH createSourceHolder(S source);

    /**
     * Should create a new instance of DH and return it.
     * @param destination The destination to base it off of, or NULL
     * @return A new instance of DH
     */
    protected abstract DH createDestinationHolder(D destination);

    /**
     * Maps DH to SH
     * @param sourceHolder The SH to populate
     * @param destinationHolder The DH to use to populate SH
     */
    protected abstract void populateSource(SH sourceHolder, DH destinationHolder);

    /**
     * Maps SH to DH
     * @param sourceHolder The SH to use to populate DH
     * @param destinationHolder The DH to populate
     */
    protected abstract void populateDestination(SH sourceHolder, DH destinationHolder);

    /**
     * Maps Bundle to DH
     * @param destinationHolder The DH to populate
     * @param bundle The Bundle to populate from
     */
    protected void populateDestinationFromBundle(DH destinationHolder, Bundle bundle) {}

    /**
     * Maps Bundle to SH
     * @param sourceHolder The SH to populate
     * @param bundle The Bundle to populate from
     */
    protected void populateSourceFromBundle(SH sourceHolder, Bundle bundle) {}

}
