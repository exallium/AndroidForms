package com.exallium.AndroidForms;

import android.os.Bundle;

public final class Form<S extends Source, D extends Drain> {

    /**
     * Maps a Source and Drain.  Should contain no state.
     * Thus, for a given S1, D1, applying a map function should always result
     * in the same S2, D2.
     * New objects should not be created, they will both be handed to you.
     * @param <S> The Source Type
     * @param <D> The Drain Type
     */
    public interface Mapper<S, D> {
        /**
         * Map from Source to Drain
         * @param source The source to map from
         * @param drain The drain to map to
         */
        void mapForward(S source, D drain);

        /**
         * Map from Drain to Source
         * @param drain the Drain to Map from
         * @param source the Source to Map to
         */
        void mapBackward(D drain, S source);
    }

    public static final class Builder<S extends Source, D extends Drain> {
        private S source;
        private D drain;
        private Mapper<S, D> mapper;
        private Mapper<Bundle, S> bundleSMapper;
        private Mapper<Bundle, D> bundleDMapper;

        /**
         * Builder Constructor
         * @param source The Source of Form information
         * @param drain Where the information is going
         * @param mapper A Source to Drain mapper
         */
        public Builder(S source, D drain, Mapper<S, D> mapper) {
            this.source = source;
            this.drain = drain;
            this.mapper = mapper;
        }

        /**
         * Add Source Extras Mapper. mapBackward is currently never called on this bundle
         * and thus does not require an implementation.
         *
         * This item is optional
         *
         * @param bundleSMapper The Mapper for adding bundle info to Source
         * @return this
         */
        public Builder withSourceExtrasMapper(Mapper<Bundle, S> bundleSMapper) {
            this.bundleSMapper = bundleSMapper;
            return this;
        }

        /**
         * Add Drain Extras Mapper.  mapBackward is currently never called on this bundle
         * and thus does not require an implementation.
         *
         * This item is optional
         *
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
            if (mapper == null) throw new IllegalArgumentException("Must Provide Mapper<D,S>");
        }

        public Form<S, D> build() {
            validate();
            return new Form<>(this);
        }

    }

    private final S source;
    private final D drain;
    private final Mapper<S, D> mapper;
    private final Mapper<Bundle, S> bundleSMapper;
    private final Mapper<Bundle, D> bundleDMapper;

    /**
     * Creates a form
     */
    protected Form(Builder<S, D> builder) {
        source = builder.source;
        drain = builder.drain;
        mapper = builder.mapper;
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
            bundleSMapper.mapForward(bundle, source);
        mapper.mapBackward(drain, source);
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
    public boolean save() {
        return save(null);
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
            bundleDMapper.mapForward(bundle, drain);
        mapper.mapForward(source, drain);
        drain.save();
    }

}
