package com.exallium.AndroidForms.samples.controller;

import android.os.Bundle;
import com.exallium.AndroidForms.Form;
import com.exallium.AndroidForms.samples.model.AuthModel;
import com.exallium.AndroidForms.samples.view.LoginView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class AuthFormFactoryTest {

    private static final String PASSWORD = "password";
    private static final String EMAIL = "user@place.ca";
    private static final String PASSWORD_KEY = "password";
    private static final String EMAIL_KEY = "email";

    private LoginView loginView;
    private AuthModel authModel;

    @Before
    public void setUp() {
        loginView = new LoginView(Robolectric.application);
        authModel = new AuthModel();
    }

    @Test
    public void testCreateLoginViewSource() {
        assertNotNull(loginView);
        AuthFormFactory.LoginViewSource loginViewSource = new AuthFormFactory.LoginViewSource(loginView);
        assertEquals(loginView, loginViewSource.getSource());
    }

    @Test
    public void testCreateAuthModelDrain() {
        assertNotNull(authModel);
        AuthFormFactory.AuthModelDrain authModelDrain = new AuthFormFactory.AuthModelDrain(authModel);
        assertEquals(authModel, authModelDrain.getDrain());
    }

    public void testCreateAuthModelDrainNull() {
        AuthFormFactory.AuthModelDrain authModelDrain = new AuthFormFactory.AuthModelDrain(null);
        assertNotNull(authModelDrain.getDrain());
    }

    @Test
    public void testFormDisplayNoData() throws Exception {
        Form form = AuthFormFactory.createForm(loginView, authModel);
        form.display();
        assertEquals("", loginView.getEmailField().getText().toString());
        assertEquals("", loginView.getPasswordField().getText().toString());
    }

    @Test
    public void testFormDisplayModelData() {
        Form form = AuthFormFactory.createForm(loginView, authModel);
        authModel.setPassword(PASSWORD);
        authModel.setUsername(EMAIL);
        form.display();
        assertEquals(EMAIL, loginView.getEmailField().getText().toString());
        assertEquals(PASSWORD, loginView.getPasswordField().getText().toString());
    }

    @Test
    public void testFormDisplayBundleData() {
        Form form = new Form.Builder<>(
                new AuthFormFactory.LoginViewSource(loginView),
                new AuthFormFactory.AuthModelDrain(authModel),
                new AuthFormFactory.AuthMapper()
        ).withSourceExtrasMapper(new Form.Mapper<Bundle, AuthFormFactory.LoginViewSource>() {
            @Override
            public void mapForward(Bundle source, AuthFormFactory.LoginViewSource drain) {
                drain.getSource().getEmailField().setText(source.getString(EMAIL_KEY));
                drain.getSource().getPasswordField().setText(source.getString(PASSWORD_KEY));
            }

            @Override
            public void mapBackward(AuthFormFactory.LoginViewSource drain, Bundle source) {
                // Unneeded
            }
        }).build();

        Bundle bundle = new Bundle();
        bundle.putString(EMAIL_KEY, EMAIL);
        bundle.putString(PASSWORD_KEY, PASSWORD);

        form.display(bundle);
        assertEquals(EMAIL, loginView.getEmailField().getText().toString());
        assertEquals(PASSWORD, loginView.getPasswordField().getText().toString());
    }

    public void testFormSaveNoData() {
        Form form = AuthFormFactory.createForm(loginView, authModel);
        assertFalse(form.save());
    }

    public void testFormSaveViewData() {
        Form form = AuthFormFactory.createForm(loginView, authModel);
        loginView.getEmailField().setText(EMAIL);
        loginView.getPasswordField().setText(PASSWORD);
        assertTrue(form.save());
        assertEquals(EMAIL, authModel.getUsername());
        assertEquals(PASSWORD, authModel.getPassword());
    }

    public void testFormForceSaveBundleData() {
        Form form = new Form.Builder<>(
                new AuthFormFactory.LoginViewSource(loginView),
                new AuthFormFactory.AuthModelDrain(authModel),
                new AuthFormFactory.AuthMapper()
        ).withDrainExtrasMapper(new Form.Mapper<Bundle, AuthFormFactory.AuthModelDrain>() {
            @Override
            public void mapForward(Bundle source, AuthFormFactory.AuthModelDrain drain) {
                drain.getDrain().setUsername(source.getString(EMAIL_KEY));
                drain.getDrain().setPassword(source.getString(PASSWORD_KEY));
            }

            @Override
            public void mapBackward(AuthFormFactory.AuthModelDrain drain, Bundle source) {
                // Unneeded
            }
        }).build();

        Bundle bundle = new Bundle();
        bundle.putString(EMAIL_KEY, EMAIL);
        bundle.putString(PASSWORD_KEY, PASSWORD);

        form.forceSave(bundle);
        assertEquals(EMAIL, authModel.getUsername());
        assertEquals(PASSWORD, authModel.getPassword());
    }
}