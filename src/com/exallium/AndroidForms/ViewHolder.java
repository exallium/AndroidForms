package com.exallium.AndroidForms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class ViewHolder extends SourceHolder<View> {

    private final Context context;
    private final int layoutId;
    private View view = null;

    public ViewHolder(Context context, int layoutId) {
        super(null);
        this.context = context;
        this.layoutId = layoutId;
    }

    public ViewHolder(View source) {
        super(source);
        context = source != null ? source.getContext() : null;
        layoutId = -1;
        view = source;
    }

    @Override
    protected View onCreate() {
        if (view == null && context != null && layoutId != -1) {
            view = LayoutInflater.from(context).inflate(layoutId, null);
        }
        return view;
    }

}
