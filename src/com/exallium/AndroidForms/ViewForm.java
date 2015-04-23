package com.exallium.AndroidForms;

import android.content.Context;
import android.view.View;

public abstract class ViewForm<D, VH extends ViewHolder, DH extends DestinationHolder<D>> extends Form<View, D, VH, DH> {

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


}
