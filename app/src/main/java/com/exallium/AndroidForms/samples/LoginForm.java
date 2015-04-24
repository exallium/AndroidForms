package com.exallium.AndroidForms.samples;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import com.exallium.AndroidForms.DestinationHolder;
import com.exallium.AndroidForms.Form;
import com.exallium.AndroidForms.R;
import com.exallium.AndroidForms.ViewHolder;
import com.exallium.AndroidForms.validators.EditTextIsEmailValidator;
import com.exallium.AndroidForms.validators.EditTextNotEmptyValidator;

public class LoginForm extends Form<View, LoginForm.AuthData> {

    protected LoginForm(Builder builder) {
        super(builder);
    }

    public static class AuthData {
        String email;
        String password;
    }

    public static Mapper<View, LoginForm.AuthData> sdMapper = new Mapper<View, AuthData>() {
        @Override
        public void map(View from, AuthData to) {
            LoginViewHolder viewHolder = (LoginViewHolder) from.getTag();
            to.email = viewHolder.email.getText().toString();
            to.password = viewHolder.password.getText().toString();
        }
    };

    public static Mapper<LoginForm.AuthData, View> dsMapper = new Mapper<AuthData, View>() {

        @Override
        public void map(AuthData from, View to) {
            LoginViewHolder viewHolder = (LoginViewHolder) to.getTag();
            viewHolder.email.setText(from.email);
            viewHolder.password.setText(from.password);
        }
    };

    public static class LoginViewHolder extends ViewHolder {

        private EditText email;
        private EditText password;

        public LoginViewHolder(Context context, int resId) {
            super(context, resId);
        }

        @Override
        protected void onViewCreated(View view) {
            email = (EditText) view.findViewById(R.id.login_email);
            password = (EditText) view.findViewById(R.id.login_password);
            addValidator(new EditTextIsEmailValidator(email));
            addValidator(new EditTextNotEmptyValidator(password));
            view.setTag(this);
        }
    }

    public static class AuthDataHolder extends DestinationHolder<AuthData> {

        public AuthDataHolder(AuthData destination) {
            super(destination);
        }

        @Override
        protected void onSave(AuthData destination) {
            // Log in the user?!
        }

        @Override
        protected AuthData onCreate() {
            return new AuthData();
        }
    }

    public static class Builder extends AbstractBuilder<LoginForm, View, AuthData, Builder> {

        @Override
        protected LoginForm createInstance() {
            return new LoginForm(this);
        }

        @Override
        protected Builder getBuilder() {
            return this;
        }

    }

    public static LoginForm create(Context context, AuthData initialData) {
        return new Builder()
                .from(new LoginViewHolder(context, R.layout.form_login))
                .to(new AuthDataHolder(initialData))
                .withDSMapper(dsMapper)
                .withSDMapper(sdMapper)
                .build();
    }


}
