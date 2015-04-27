package com.exallium.AndroidForms.samples.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.exallium.AndroidForms.R;

public class LoginView extends LinearLayout {

    private EditText emailField;
    private EditText passwordField;

    public LoginView(Context context) {
        super(context);
        initView();
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.form_login, this);
        emailField = (EditText) findViewById(R.id.login_email);
        passwordField = (EditText) findViewById(R.id.login_password);
    }

    public EditText getEmailField() {
        return emailField;
    }

    public EditText getPasswordField() {
        return passwordField;
    }
}
