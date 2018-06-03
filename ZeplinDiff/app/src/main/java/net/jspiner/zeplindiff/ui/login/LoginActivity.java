package net.jspiner.zeplindiff.ui.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.widget.Toast;

import net.jspiner.zeplindiff.R;
import net.jspiner.zeplindiff.databinding.ActivityLoginBinding;
import net.jspiner.zeplindiff.ui.base.BaseActivity;
import net.jspiner.zeplindiff.ui.project.ProjectActivity;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, Contract.Presenter> implements Contract.View {

    private static final int REQUEST_CODE_SCREEN_PERMISSION = 1004;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected Contract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        initViews();
        checkScreenPermission();
    }

    private void initViews() {
        binding.login.setOnClickListener(__ -> presenter.onLoginButtonClicked(
                binding.id.getText().toString(),
                binding.pw.getText().toString()
        ));
    }

    private void checkScreenPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || isScreenPermissionGranted()) {
            checkLogin();
        } else {
            requestScreenPermission();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isScreenPermissionGranted() {
        return Settings.canDrawOverlays(this);
    }

    private void checkLogin() {
        if (presenter.isLogined()) {
            startProjectActivity();
        }
    }

    private void requestScreenPermission() {
        Intent intent = new Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getPackageName())
        );
        startActivityForResult(intent, REQUEST_CODE_SCREEN_PERMISSION);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCREEN_PERMISSION) {
            checkScreenPermission();
        }
    }

    @Override
    public void startProjectActivity() {
        Intent intent = new Intent(this, ProjectActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showInputInvaildToast() {
        Toast.makeText(this, "Id/Pw를 입력해주세요", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showIdPwDismatchToast() {
        Toast.makeText(getBaseContext(), "Id 혹은 Pw가 잘못되었습니다.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoginSuccessToast() {
        Toast.makeText(getBaseContext(), "로그인 성공", Toast.LENGTH_LONG).show();
    }
}


