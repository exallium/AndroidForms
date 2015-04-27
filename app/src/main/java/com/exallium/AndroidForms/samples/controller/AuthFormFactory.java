package com.exallium.AndroidForms.samples.controller;

import com.exallium.AndroidForms.Drain;
import com.exallium.AndroidForms.Form;
import com.exallium.AndroidForms.ViewSource;
import com.exallium.AndroidForms.samples.model.AuthModel;
import com.exallium.AndroidForms.samples.view.LoginView;
import com.exallium.AndroidForms.validators.EditTextIsEmailValidator;
import com.exallium.AndroidForms.validators.EditTextNotEmptyValidator;

public class AuthFormFactory {

    public static Form<LoginView, AuthModel> createForm(LoginView view, AuthModel model) {
        return new Form.Builder<LoginView, AuthModel>(new LoginViewSource(view), new AuthModelDrain(model), new AuthMapper()).build();
    }

    private static class LoginViewSource extends ViewSource<LoginView> {

        public LoginViewSource(LoginView source) {
            super(source);
        }

        @Override
        protected void onViewCreated(LoginView view) {
            addValidator(new EditTextIsEmailValidator(view.getEmailField()));
            addValidator(new EditTextNotEmptyValidator(view.getPasswordField()));
        }
    }

    private static class AuthModelDrain extends Drain<AuthModel> {

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

    private static class AuthMapper implements Form.Mapper<LoginViewSource, AuthModelDrain> {

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
