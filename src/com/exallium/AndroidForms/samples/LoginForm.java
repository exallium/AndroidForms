package com.exallium.AndroidForms.samples;

import android.view.View;
import android.widget.EditText;
import com.exallium.AndroidForms.DestinationHolder;
import com.exallium.AndroidForms.R;
import com.exallium.AndroidForms.ViewForm;
import com.exallium.AndroidForms.ViewHolder;

public class LoginForm extends ViewForm<LoginForm.AuthDetails, LoginForm.AuthViewHolder, LoginForm.AuthHolder> {

    public class Builder extends ViewForm.Builder<LoginForm, AuthDetails> {

        @Override
        protected LoginForm create() {
            return new LoginForm(this);
        }
    }

    protected LoginForm(Builder builder) {
        super(builder);
    }

    @Override
    protected AuthViewHolder createSourceHolder(View source) {
        return new AuthViewHolder(source);
    }

    @Override
    protected AuthHolder createDestinationHolder(AuthDetails destination) {
        return new AuthHolder(destination);
    }

    @Override
    protected void populateSource(AuthViewHolder sourceHolder, AuthHolder destinationHolder) {
        sourceHolder.password.setText(destinationHolder.getDestination().password);
        sourceHolder.username.setText(destinationHolder.getDestination().username);
    }

    @Override
    protected void populateDestination(AuthViewHolder sourceHolder, AuthHolder destinationHolder) {
        AuthDetails details = destinationHolder.getDestination();
        details.password = sourceHolder.password.getText().toString();
        details.username = sourceHolder.password.getText().toString();
    }

    public static class AuthDetails {
        public String username;
        public String password;
    }

    public static class AuthHolder extends DestinationHolder<LoginForm.AuthDetails> {

        public AuthHolder(AuthDetails destination) {
            super(destination);
        }

        @Override
        protected void onSave(AuthDetails destination) {
        }

        @Override
        protected AuthDetails onCreate() {
            return new AuthDetails();
        }
    }

    public static class AuthViewHolder extends ViewHolder {

        private View view;

        private EditText username;
        private EditText password;

        public AuthViewHolder(View source) {
            super(source);
        }

        @Override
        protected View onCreate() {
            view = super.onCreate();
            username = (EditText) view.findViewById(R.id.login_username);
            password = (EditText) view.findViewById(R.id.login_password);
            return view;
        }

        @Override
        protected boolean isValid(View source) {
            boolean valid = username.getText().toString().length() != 0;
            return valid && password.getText().toString().length() != 0;
        }
    }


}
