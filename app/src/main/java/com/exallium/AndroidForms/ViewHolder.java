package com.exallium.AndroidForms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class ViewHolder extends SourceHolder<View> {

    private Context context;
    private int resId;

    public ViewHolder(Context context, int resId) {
        super(null);
    }

    public ViewHolder(View source) {
        super(source);
        onViewCreated(source);
    }

    @Override
    protected View onCreate() {
        View view = LayoutInflater.from(context).inflate(resId, null);
        onViewCreated(view);
        return view;
    }

    /**
     * Add validators in here!
     */
    protected abstract void onViewCreated(View view);
}
