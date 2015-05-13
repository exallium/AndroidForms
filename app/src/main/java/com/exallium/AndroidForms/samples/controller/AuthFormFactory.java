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

package com.exallium.AndroidForms.samples.controller;

import com.exallium.AndroidForms.Drain;
import com.exallium.AndroidForms.Form;
import com.exallium.AndroidForms.ViewSource;
import com.exallium.AndroidForms.samples.model.AuthModel;
import com.exallium.AndroidForms.samples.view.LoginView;
import com.exallium.AndroidForms.validators.EditTextIsEmailValidator;
import com.exallium.AndroidForms.validators.EditTextNotEmptyValidator;
import com.exallium.AndroidForms.validators.Validator;

import java.util.Arrays;
import java.util.List;

public class AuthFormFactory {

    public static Form createForm(LoginView view, AuthModel model) {
        return new Form.Builder<>(new LoginViewSource(view), new AuthModelDrain(model), new AuthMapper()).build();
    }

    static class LoginViewSource extends ViewSource<LoginView> {

        public LoginViewSource(LoginView source) {
            super(source);
        }

        @Override
        protected List<? extends Validator> onSourceCreated() {
            return Arrays.asList(new EditTextIsEmailValidator(getSource().getEmailField()),
                    new EditTextNotEmptyValidator(getSource().getPasswordField()));
        }
    }

    static class AuthModelDrain extends Drain<AuthModel> {

        public AuthModelDrain(AuthModel drain) {
            super(drain);
        }

        @Override
        protected void onSave(AuthModel drain) {
            // Do Save?
        }

        @Override
        protected AuthModel onCreate() {
            return new AuthModel();
        }
    }

    static class AuthMapper implements Form.Mapper<LoginViewSource, AuthModelDrain> {

        @Override
        public void mapForward(LoginViewSource source, AuthModelDrain drain) {
            LoginView s = source.getSource();
            AuthModel d = drain.getDrain();
            d.setUsername(s.getEmailField().getText().toString());
            d.setPassword(s.getPasswordField().getText().toString());
        }

        @Override
        public void mapBackward(AuthModelDrain drain, LoginViewSource source) {
            LoginView s = source.getSource();
            AuthModel d = drain.getDrain();
            s.getEmailField().setText(d.getUsername());
            s.getPasswordField().setText(d.getPassword());
        }
    }

}
