package com.exallium.AndroidForms;

import android.os.Bundle;

public class Form<S, D> {

    public interface Mapper<F, T> {
        void map(F from, T to);
    }

    public static class Builder<S, D> extends AbstractBuilder<Form<S, D>, S, D, Builder<S, D>> {

        @Override
        protected Form<S, D> createInstance() {
            return new Form<S, D>(this);
        }

        @Override
        protected Builder<S, D> getBuilder() {
            return this;
        }
    }

    public abstract static class AbstractBuilder<F, S, D, B> {
        private SourceHolder<S> sourceHolder;
        private DestinationHolder<D> destinationHolder;
        private Mapper<S, D> sdMapper;
        private Mapper<D, S> dsMapper;
        private Mapper<Bundle, S> bundleSMapper;
        private Mapper<Bundle, D> bundleDMapper;

        public B from(SourceHolder<S> sourceHolder) {
            this.sourceHolder = sourceHolder;
            return getBuilder();
        }

        public B to(DestinationHolder<D> destinationHolder) {
            this.destinationHolder = destinationHolder;
            return getBuilder();
        }

        public B withSDMapper(Mapper<S, D> sdMapper) {
            this.sdMapper = sdMapper;
            return getBuilder();
        }

        public B withDSMapper(Mapper<D, S> dsMapper) {
            this.dsMapper = dsMapper;
            return getBuilder();
        }

        public B withSourceExtrasMapper(Mapper<Bundle, S> bundleSMapper) {
            this.bundleSMapper = bundleSMapper;
            return getBuilder();
        }

        public B withDestinationExtrasMapper(Mapper<Bundle, D> bundleDMapper) {
            this.bundleDMapper = bundleDMapper;
            return getBuilder();
        }

        protected void validate() {
            if (sourceHolder == null) throw new IllegalArgumentException("Must Provide SourceHolder<S>");
            if (destinationHolder == null) throw new IllegalArgumentException("Must Provide DestinationHolder<S>");
            if (sdMapper == null) throw new IllegalArgumentException("Must Provide Mapper<S,D>");
            if (dsMapper == null) throw new IllegalArgumentException("Must Provide Mapper<D,S>");
        }

        protected abstract F createInstance();
        protected abstract B getBuilder();

        public final F build() {
            validate();
            return createInstance();
        }

    }

    private final SourceHolder<S> sourceHolder;
    private final DestinationHolder<D> destinationHolder;
    private final Mapper<S, D> sdMapper;
    private final Mapper<D, S> dsMapper;
    private final Mapper<Bundle, S> bundleSMapper;
    private final Mapper<Bundle, D> bundleDMapper;

    /**
     * Creates a form
     */
    protected Form(AbstractBuilder<? extends Form<S, D>, S, D, ? extends AbstractBuilder> builder) {
        sourceHolder = builder.sourceHolder;
        destinationHolder = builder.destinationHolder;
        sdMapper = builder.sdMapper;
        dsMapper = builder.dsMapper;
        bundleSMapper = builder.bundleSMapper;
        bundleDMapper = builder.bundleDMapper;
    }

    /**
     * Populates the source with information from the destination, and then
     * adds information form the given bundle if there is one.
     * @param bundle The bundle of extra data
     */
    public final void display(Bundle bundle) {
        if (bundle != null && bundleSMapper != null)
            bundleSMapper.map(bundle, sourceHolder.getSource());
        dsMapper.map(destinationHolder.getDestination(), sourceHolder.getSource());
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
        boolean isValid = sourceHolder.onValidate();
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
        if (bundle != null && bundleDMapper != null)
            bundleDMapper.map(bundle, destinationHolder.getDestination());
        sdMapper.map(sourceHolder.getSource(), destinationHolder.getDestination());
        destinationHolder.save();
    }


}
