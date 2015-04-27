package com.exallium.AndroidForms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class ViewSource extends Source<View> {

    private Context context;
    private int resId;

    /**
     * When we want to inflate a view within and get it via getSource
     * @param context The context we're creating from
     * @param resId The layout id
     */
    public ViewSource(Context context, int resId) {
        super(null);
    }

    /**
     * When we just want to validate part of an already created view
     * @param source
     */
    public ViewSource(View source) {
        super(source);
        onViewCreated(source);
    }

    /**
     * We inflate our view here, unless one was passed in in second constructor.
     * @return Our inflated view
     */
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
