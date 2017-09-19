package net.jspiner.zeplindiff;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.jspiner.zeplindiff.api.Api;
import net.jspiner.zeplindiff.model.User;
import net.jspiner.zeplindiff.project.ProjectActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {

        if(KeyManager.getToken() != null){

            Intent intent = new Intent(LoginActivity.this, ProjectActivity.class);
            startActivity(intent);
            finish();
        }

        final EditText id = (EditText) findViewById(R.id.id);
        final EditText pw = (EditText) findViewById(R.id.pw);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Api.getService().login(
                        id.getText().toString(),
                        pw.getText().toString()
                ).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d("login", "response : " + response.body());
                        switch (response.code()){
                            case 412:
                                Toast.makeText(getBaseContext(), "로그인 실패", Toast.LENGTH_LONG).show();
                                break;
                            case 200:
                                Toast.makeText(getBaseContext(), "로그인 성공", Toast.LENGTH_LONG).show();

                                KeyManager.putToken(response.body().token);

                                Intent intent = new Intent(LoginActivity.this, ProjectActivity.class);
                                startActivity(intent);

                                break;

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

            }
        });



    }

}


