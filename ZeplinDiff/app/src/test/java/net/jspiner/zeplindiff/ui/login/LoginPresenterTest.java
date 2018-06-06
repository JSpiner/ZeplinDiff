package net.jspiner.zeplindiff.ui.login;

import android.preference.PreferenceManager;

import junit.framework.Assert;

import net.jspiner.zeplindiff.BuildConfig;
import net.jspiner.zeplindiff.utils.KeyManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class LoginPresenterTest {

    private final String TEST_LOGIN_KEY = "TEST_LOGIN_TOKEN";
    private final String TEST_ACCOUNT_ID = "test@test.com";
    private final String TEST_ACCOUNT_PW = "test1234";

    private LoginPresenter presenter;
    @Mock
    private Contract.View view;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        setUpKeyManager();

        presenter = new LoginPresenter(view);
        presenter.attachView();
    }

    private void setUpKeyManager() throws NoSuchFieldException, IllegalAccessException {
        Field field = KeyManager.class.getDeclaredField("sharedPreferences");
        field.setAccessible(true);
        field.set(
                null,
                PreferenceManager.getDefaultSharedPreferences(
                        RuntimeEnvironment.application.getApplicationContext()
                )
        );

        KeyManager.clear();
    }

    @Test
    public void isLogined_LoginedStatusTest() {
        KeyManager.clear();
        KeyManager.putToken(TEST_LOGIN_KEY);

        Assert.assertEquals(
                true,
                presenter.isLogined()
        );
    }

    @Test
    public void isLogined_NotLoginedStatusTest() {
        KeyManager.clear();

        Assert.assertEquals(
                false,
                presenter.isLogined()
        );
    }

    @Test
    public void onLoginButtonClick_EmptyIdTest() {
        presenter.onLoginButtonClicked(
                null,
                TEST_ACCOUNT_PW
        );
        verify(view).showInputInvaildToast();
    }

    @Test
    public void onLoginButtonClick_EmptyPwTest() {
        presenter.onLoginButtonClicked(
                TEST_ACCOUNT_ID,
                null
        );
        verify(view).showInputInvaildToast();
    }

    @Test
    public void onLoginButtonClick_EmptyIdPwTest() {
        presenter.onLoginButtonClicked(
                null,
                null
        );
        verify(view).showInputInvaildToast();
    }

    @Test
    public void onLoginButtonClick_DismatchIdPwTest() {
        presenter.onLoginButtonClicked(
                TEST_ACCOUNT_ID,
                TEST_ACCOUNT_PW
        );
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        verify(view).showIdPwDismatchToast();
    }

}
