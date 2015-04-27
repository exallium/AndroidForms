package com.exallium.AndroidForms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public abstract class ViewSource<V extends View> extends Source<V> {

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
    public ViewSource(V source) {
        super(source);
        onViewCreated(source);
    }

    /**
     * We inflate our view here, unless one was passed in in second constructor.
     * @return Our inflated view
     */
    @Override
    @SuppressWarnings({"unchecked"})
    protected V onCreate() {
        V view = (V) LayoutInflater.from(context).inflate(resId, null);
        onViewCreated(view);
        return view;
    }

    /**
     * Add validators in here!
     */
    protected abstract void onViewCreated(V view);
}
