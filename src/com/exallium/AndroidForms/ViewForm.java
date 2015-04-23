package com.exallium.AndroidForms;

import android.content.Context;
import android.view.View;

public abstract class ViewForm<VH extends ViewForm.ViewHolder, E, EH extends Form.EntityHolder<E>> extends Form<E, EH> {

    private final ViewHolder viewHolder;

    public ViewForm(Context context, E entity) {
        super(entity);
        this.viewHolder = getViewHolder(context);
        populateFields(entity);
    }

    public ViewForm(Context context) {
        super();
        this.viewHolder = getViewHolder(context);
    }

    protected abstract ViewHolder getViewHolder(Context context);

    public static class ViewHolder {

        private final View view;

        protected ViewHolder(Context context) {
            this.view = getView(context);
        }

        protected View getView(Context context) { return null; }

    }
}
