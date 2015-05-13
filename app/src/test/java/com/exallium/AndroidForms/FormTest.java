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

import android.os.Bundle;
import com.exallium.AndroidForms.validators.IsEmailValidator;
import com.exallium.AndroidForms.validators.Validator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FormTest {

    private static final String EMAIL = "user@place.ca";
    private static final String PASSWORD = "password";
    private static final String EXTRA = "extra";

    private static final String EMAIL_KEY = "email_key";
    private static final String PASSWORD_KEY = "password_key";
    private static final String EXTRA_KEY = "extra_key";

    private Bundle bundle;

    private static class AuthModel {
        private String email;
        private String password;
        private String extra;
    }

    private static class AuthModelSource extends Source<AuthModel> {

        public AuthModelSource(AuthModel source) {
            super(source);
        }

        @Override
        protected AuthModel onCreate() {
            return new AuthModel();
        }

        @Override
        protected List<? extends Validator> onSourceCreated() {
            return Arrays.asList(
                new IsEmailValidator<AuthModel>(getSource()) {
                    @Override
                        protected String getString(AuthModel field) {
                            return field.email;
                        }
                    },
                new Validator<AuthModel>(getSource()) {
                    @Override
                    protected boolean onValidate(AuthModel field) {
                            return field.password.length() != 0;
                        }
                    }
            );
        }

    }

    private class AuthModelDrain extends Drain<AuthModel> {

        public AuthModelDrain(AuthModel drain) {
            super(drain);
        }

        @Override
        protected void onSave(AuthModel drain) {
            // do nothing
        }

        @Override
        protected AuthModel onCreate() {
            return new AuthModel();
        }
    }

    private Form.Mapper<AuthModelSource, AuthModelDrain> mapper = new Form.Mapper<AuthModelSource, AuthModelDrain>() {

        @Override
        public void mapForward(AuthModelSource source, AuthModelDrain drain) {
            drain.getDrain().email = source.getSource().email;
            drain.getDrain().password = source.getSource().password;
        }

        @Override
        public void mapBackward(AuthModelDrain drain, AuthModelSource source) {
            source.getSource().email = drain.getDrain().email;
            source.getSource().password = drain.getDrain().password;
        }
    };

    private Form.Mapper<Bundle, AuthModelSource> bundleAuthModelSourceMapper = new Form.Mapper<Bundle, AuthModelSource>() {
        @Override
        public void mapForward(Bundle source, AuthModelSource drain) {
            drain.getSource().email = source.getString(EMAIL_KEY);
            drain.getSource().password = source.getString(PASSWORD_KEY);
        }

        @Override
        public void mapBackward(AuthModelSource drain, Bundle source) {
            // Not used
        }
    };

    private Form.Mapper<Bundle, AuthModelDrain> bundleAuthModelDrainMapper = new Form.Mapper<Bundle, AuthModelDrain>() {
        @Override
        public void mapForward(Bundle source, AuthModelDrain drain) {
            drain.getDrain().email = source.getString(EMAIL_KEY);
            drain.getDrain().password = source.getString(PASSWORD_KEY);
            drain.getDrain().extra = source.getString(EXTRA_KEY);
        }

        @Override
        public void mapBackward(AuthModelDrain drain, Bundle source) {
            // Not Used
        }
    };

    @Before
    public void setUp() {
        bundle = Mockito.mock(Bundle.class);
        Mockito.when(bundle.getString(EMAIL_KEY)).thenReturn(EMAIL);
        Mockito.when(bundle.getString(PASSWORD_KEY)).thenReturn(PASSWORD);
        Mockito.when(bundle.getString(EXTRA_KEY)).thenReturn(EXTRA);
    }

    @Test
    public void testDisplay() throws Exception {
        AuthModelSource source = new AuthModelSource(null);
        Form form = new Form.Builder<>(source, new AuthModelDrain(null), mapper).build();

        form.display();

        assertNotNull(source.getSource());
        assertNull(source.getSource().email);
        assertNull(source.getSource().password);
    }

    @Test
    public void testDisplayWithBundle() throws Exception {
        AuthModelSource source = new AuthModelSource(null);
        Form form = new Form.Builder<>(source, new AuthModelDrain(null), mapper)
                .withSourceExtrasMapper(bundleAuthModelSourceMapper).build();

        form.display(bundle);

        assertNotNull(source.getSource());
        assertEquals(EMAIL, source.getSource().email);
        assertEquals(PASSWORD, source.getSource().password);
    }

    @Test
    public void testSave() throws Exception {
        AuthModelSource source = new AuthModelSource(null);
        AuthModelDrain drain = new AuthModelDrain(null);
        Form form = new Form.Builder<>(source, drain, mapper).build();

        assertNull(drain.getDrain());

        form.display();

        assertFalse(form.save());

        source.getSource().email = EMAIL;
        source.getSource().password = PASSWORD;

        assertTrue(form.save());

        assertEquals(EMAIL, drain.getDrain().email);
        assertEquals(PASSWORD, drain.getDrain().password);
    }

    @Test
    public void testForceSave() throws Exception {
        AuthModelSource source = new AuthModelSource(null);
        AuthModelDrain drain = new AuthModelDrain(null);
        Form form = new Form.Builder<>(source, drain, mapper).build();

        form.forceSave();

        assertNotNull(drain.getDrain());

        source.getSource().password = PASSWORD;
        source.getSource().email = "INVALID";

        form.forceSave();

        assertEquals("INVALID", drain.getDrain().email);
        assertEquals(PASSWORD, drain.getDrain().password);

    }

    @Test
    public void testForceSaveWithBundle() throws Exception {
        AuthModelDrain drain = new AuthModelDrain(null);
        Form form = new Form.Builder<>(new AuthModelSource(null), drain, mapper)
                .withDrainExtrasMapper(bundleAuthModelDrainMapper).build();

        form.forceSave(bundle);
        assertNotNull(drain.getDrain());
        assertNull(drain.getDrain().email);
        assertNull(drain.getDrain().password);
        assertEquals(EXTRA, drain.getDrain().extra);

    }
}