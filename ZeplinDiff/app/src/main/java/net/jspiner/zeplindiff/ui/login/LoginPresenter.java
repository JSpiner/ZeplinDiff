package net.jspiner.zeplindiff.ui.login;

import android.os.Build;
import android.text.TextUtils;

import net.jspiner.zeplindiff.utils.KeyManager;
import net.jspiner.zeplindiff.api.Api;
import net.jspiner.zeplindiff.model.User;
import net.jspiner.zeplindiff.ui.base.BasePresenter;

import retrofit2.Response;

public class LoginPresenter extends BasePresenter<Contract.View> implements Contract.Presenter {

    private static final int RESPONSE_CODE_USER_NOT_FOUND = 412;
    private static final int RESPONSE_CODE_LOGIN_SUCCESS = 200;

    public LoginPresenter(Contract.View view) {
        super(view);
    }

    @Override
    public void attachView() {
        init();
    }

    private void init() {
        checkScreenPermission();
    }

    @Override
    public void checkScreenPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || view.isScreenPermissionGranted()) {
            checkLogin();
        } else {
            view.requestScreenPermission();
        }
    }

    private void checkLogin() {
        if (KeyManager.getToken() != null) {
            view.startProjectActivity();
        }
    }

    @Override
    public void requestLogin() {
        if (isUserInputVaild() == false) {
            view.showInputInvaildToast();
            return;
        }

        Api.getService().login(view.getUserId(), view.getUserPw())
                .compose(networkTransformer())
                .compose(disposeTransformer())
                .subscribe(
                        userResponse -> onLoginResponse(userResponse)
                );
    }

    private boolean isUserInputVaild() {
        return TextUtils.isEmpty(view.getUserId()) == false &&
                TextUtils.isEmpty(view.getUserPw()) == false;
    }

    private void onLoginResponse(Response<User> userResponse) {
        switch (userResponse.code()) {
            case RESPONSE_CODE_USER_NOT_FOUND:
                view.showIdPwDismatchToast();
                break;

            case RESPONSE_CODE_LOGIN_SUCCESS:
                view.showLoginSuccessToast();
                view.startProjectActivity();

                saveUserToken(userResponse.body().token);
                break;

            default:
                view.showIdPwDismatchToast();
                break;
        }
    }

    private void saveUserToken(String token) {
        KeyManager.putToken(token);
    }
}
