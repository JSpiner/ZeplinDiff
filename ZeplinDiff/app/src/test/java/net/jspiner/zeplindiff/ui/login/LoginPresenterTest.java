package net.jspiner.zeplindiff.ui.login;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginPresenterTest {

    private LoginPresenter presenter;
    @Mock
    private LoginActivity view;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(view.isScreenPermissionGranted()).thenReturn(true);


        presenter = new LoginPresenter(view);
        presenter.attachView();
    }

    @Test
    public void checkScreenPermissionTest() {
        presenter.checkScreenPermission();

        verify(view).startProjectActivity();
    }
}
