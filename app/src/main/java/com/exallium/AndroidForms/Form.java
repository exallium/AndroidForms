package com.exallium.AndroidForms;

import android.os.Bundle;

public final class Form<S, D> {

    /**
     * Mapper takes From (F) and To (T) arguments and puts information from F into T
     * Mapper should never store state.  Map should have no side effects.
     * For Any (F, T), map(F, T) should result in (F2, T2)
     * @param <F>
     * @param <T>
     */
    public interface Mapper<F, T> {
        void map(F from, T to);
    }

    public static final class Builder<S, D> {
        private Source<S> source;
        private Drain<D> drain;
        private Mapper<S, D> sdMapper;
        private Mapper<D, S> dsMapper;
        private Mapper<Bundle, S> bundleSMapper;
        private Mapper<Bundle, D> bundleDMapper;

        /**
         * Builder Constructor
         * @param source The Source of Form information
         * @param drain Where the information is going
         * @param sdMapper How the Source gets to the drain
         * @param dsMapper How the Drain gets to the Source (for initialization)
         */
        public Builder(Source<S> source, Drain<D> drain, Mapper<S, D> sdMapper, Mapper<D, S> dsMapper) {
            this.source = source;
            this.drain = drain;
            this.sdMapper = sdMapper;
            this.dsMapper = dsMapper;
        }

        /**
         * Add Source Extras Mapper
         * @param bundleSMapper The Mapper for adding bundle info to Source
         * @return this
         */
        public Builder withSourceExtrasMapper(Mapper<Bundle, S> bundleSMapper) {
            this.bundleSMapper = bundleSMapper;
            return this;
        }

        /**
         * Add Drain Extras Mapper
         * @param bundleDMapper The Mapper for adding bundle info to Drain
         * @return this
         */
        public Builder withDrainExtrasMapper(Mapper<Bundle, D> bundleDMapper) {
            this.bundleDMapper = bundleDMapper;
            return this;
        }

        private void validate() {
            if (source == null) throw new IllegalArgumentException("Must Provide SourceHolder<S>");
            if (drain == null) throw new IllegalArgumentException("Must Provide DrainHolder<S>");
            if (sdMapper == null) throw new IllegalArgumentException("Must Provide Mapper<S,D>");
            if (dsMapper == null) throw new IllegalArgumentException("Must Provide Mapper<D,S>");
        }

        public Form<S, D> build() {
            validate();
            return new Form<S, D>(this);
        }

    }

    private final Source<S> source;
    private final Drain<D> drain;
    private final Mapper<S, D> sdMapper;
    private final Mapper<D, S> dsMapper;
    private final Mapper<Bundle, S> bundleSMapper;
    private final Mapper<Bundle, D> bundleDMapper;

    /**
     * Creates a form
     */
    protected Form(Builder<S, D> builder) {
        source = builder.source;
        drain = builder.drain;
        sdMapper = builder.sdMapper;
        dsMapper = builder.dsMapper;
        bundleSMapper = builder.bundleSMapper;
        bundleDMapper = builder.bundleDMapper;
    }

    /**
     * Populates the source with information from the drain, and then
     * adds information form the given bundle if there is one.
     * @param bundle The bundle of extra data
     */
    public void display(Bundle bundle) {
        if (bundle != null && bundleSMapper != null)
            bundleSMapper.map(bundle, source.getSource());
        dsMapper.map(drain.getDrain(), source.getSource());
    }

    /**
     * See display(bundle)
     */
    public void display() {
        display(null);
    }

    /**
     * See save(bundle)
     */
    public void save() {
        save(null);
    }

    /**
     * see forceSave(bundle)
     */
    public void forceSave() {
        forceSave(null);
    }

    /**
     * Populates the drain with information from the source and the given
     * bundle.
     * @param bundle The bundle of extra data
     * @return whether or not data validation was successful
     */
    public boolean save(Bundle bundle) {
        boolean isValid = source.onValidate();
        if (isValid) {
            forceSave(bundle);
        }
        return isValid;
    }

    /**
     * Saves ignoring validation. Applies bundle if present
     * @param bundle The bundle of extra data
     */
    public void forceSave(Bundle bundle) {
        if (bundle != null && bundleDMapper != null)
            bundleDMapper.map(bundle, drain.getDrain());
        sdMapper.map(source.getSource(), drain.getDrain());
        drain.save();
    }


}
