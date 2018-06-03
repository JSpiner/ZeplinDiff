package net.jspiner.zeplindiff.ui.login;

import android.os.Build;
import android.support.annotation.RequiresApi;

import net.jspiner.zeplindiff.ui.base.BasePresenterInterface;
import net.jspiner.zeplindiff.ui.base.BaseViewInterface;

public interface Contract {

    interface View extends BaseViewInterface {

        void showInputInvaildToast();

        void showIdPwDismatchToast();

        void showLoginSuccessToast();

        void startProjectActivity();
    }

    interface Presenter extends BasePresenterInterface {
        void onLoginButtonClicked(String id, String password);
        boolean isLogined();
    }

}
