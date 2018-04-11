package net.jspiner.zeplindiff.ui.login;

import android.os.Build;
import android.support.annotation.RequiresApi;

import net.jspiner.zeplindiff.ui.base.BasePresenterInterface;
import net.jspiner.zeplindiff.ui.base.BaseViewInterface;

public interface Contract {

    interface View extends BaseViewInterface {

        @RequiresApi(Build.VERSION_CODES.M)
        boolean isScreenPermissionGranted();

        void requestScreenPermission();

        void startProjectActivity();

        String getUserId();

        String getUserPw();

        void showInputInvaildToast();

        void showIdPwDismatchToast();

        void showLoginSuccessToast();
    }

    interface Presenter extends BasePresenterInterface {
        void checkScreenPermission();
        void requestLogin();
    }

}
