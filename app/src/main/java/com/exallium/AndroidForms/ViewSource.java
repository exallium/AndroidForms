/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Alex Hart
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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
        onSourceCreated();
    }

    /**
     * We inflate our view here, unless one was passed in in second constructor.
     * @return Our inflated view
     */
    @Override
    @SuppressWarnings({"unchecked"})
    protected V onCreate() {
        return (V) LayoutInflater.from(context).inflate(resId, null);
    }
}
