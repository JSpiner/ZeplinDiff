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
    }

    @Override
    public boolean isLogined() {
        return KeyManager.getToken() != null;
    }

    @Override
    public void onLoginButtonClicked(String id, String password) {
        if (isUserInputVaild(id, password) == false) {
            view.showInputInvaildToast();
            return;
        }
        requestLogin(id, password);
    }

    private boolean isUserInputVaild(String id, String password) {
        return TextUtils.isEmpty(id) == false &&
                TextUtils.isEmpty(password) == false;
    }

    private void requestLogin(String id, String password) {
        Api.getService().login(id, password)
                .compose(networkTransformer())
                .compose(disposeTransformer())
                .subscribe(
                        userResponse -> onLoginResponse(userResponse)
                );
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
