package com.exallium.AndroidForms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class ViewForm<D, DH extends Form.DestinationHolder<D>> extends Form<View, D, Form.SourceHolder<View>, DH> {

    private Context context = null;
    private int layoutId = -1;

    public static abstract class Builder<F extends ViewForm, D> extends Form.Builder<F, View, D> {

        private Context context = null;
        private int  layoutId = -1;

        public Builder withContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder withLayoutId(int id) {
            this.layoutId = id;
            return this;
        }
    }

    protected ViewForm(Builder<? extends ViewForm, D> builder) {
        super(builder);
        this.context = builder.context;
        this.layoutId = builder.layoutId;
    }

    public static abstract class ViewHolder extends SourceHolder<View> {

        private final Context context;
        private final int layoutId;

        public ViewHolder(Context context, int layoutId) {
            super(null);
            this.context = context;
            this.layoutId = layoutId;
        }

        public ViewHolder(View source) {
            super(source);
            context = source != null ? source.getContext() : null;
            layoutId = -1;
        }

        @Override
        protected View onCreate() {
            // This is false if we passed a view in via ViewHolder(source)
            if (context != null && layoutId != -1) {
                return LayoutInflater.from(context).inflate(layoutId, null);

            }
            return null;
        }

    }
}
