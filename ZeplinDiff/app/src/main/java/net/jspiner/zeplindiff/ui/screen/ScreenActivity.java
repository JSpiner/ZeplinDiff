package net.jspiner.zeplindiff.ui.screen;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import net.jspiner.zeplindiff.KeyManager;
import net.jspiner.zeplindiff.R;
import net.jspiner.zeplindiff.api.Api;
import net.jspiner.zeplindiff.databinding.ActivityProjectsBinding;
import net.jspiner.zeplindiff.model.ScreenModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenActivity extends AppCompatActivity {

    private ActivityProjectsBinding binding;
    private ScreenAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_projects);

        init();
    }

    private void init() {
        adapter = new ScreenAdapter();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setAdapter(adapter);


        requestScreenList();
    }

    private void requestScreenList() {
        Log.d("token", "token : " + KeyManager.getToken());
        Api.getService().getScreenList(
                KeyManager.getToken(),
                getIntent().getStringExtra("hash_id")
        ).enqueue(new Callback<ScreenModel>() {
            @Override
            public void onResponse(Call<ScreenModel> call, Response<ScreenModel> response) {
                switch (response.code()){
                    case 200:
                        adapter.addAll(response.body().screens);
                        break;
                    default:
                        Toast.makeText(getBaseContext(), "에러", Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ScreenModel> call, Throwable t) {
                Toast.makeText(getBaseContext(), "에러" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
