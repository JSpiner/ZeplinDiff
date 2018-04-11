package net.jspiner.zeplindiff.ui.login;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.jspiner.zeplindiff.KeyManager;
import net.jspiner.zeplindiff.R;
import net.jspiner.zeplindiff.api.Api;
import net.jspiner.zeplindiff.databinding.ActivityLoginBinding;
import net.jspiner.zeplindiff.model.User;
import net.jspiner.zeplindiff.ui.base.BaseActivity;
import net.jspiner.zeplindiff.ui.base.BasePresenterInterface;
import net.jspiner.zeplindiff.ui.project.ProjectActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    }

    private void initViews() {
        binding.login.setOnClickListener(__ -> presenter.requestLogin());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean isScreenPermissionGranted() {
        return true; //Settings.canDrawOverlays(this); TODO : 권환확인 메소드가 항상 false로 나오는 이슈 확인 필요
    }

    @Override
    public void requestScreenPermission() {
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
            presenter.checkScreenPermission();
        }
    }

    @Override
    public void startProjectActivity() {
        Intent intent = new Intent(this, ProjectActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public String getUserId() {
        return binding.id.getText().toString();
    }

    @Override
    public String getUserPw() {
        return binding.pw.getText().toString();
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


