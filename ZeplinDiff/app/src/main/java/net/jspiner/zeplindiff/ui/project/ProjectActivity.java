package net.jspiner.zeplindiff.ui.project;

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
import net.jspiner.zeplindiff.model.ProjectModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectActivity extends AppCompatActivity {

    private ActivityProjectsBinding binding;
    private ProjectAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_projects);

        init();
    }

    private void init() {
        adapter = new ProjectAdapter();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setAdapter(adapter);


        requestProjectList();
    }

    private void requestProjectList() {
        Log.d("token", "token : " + KeyManager.getToken());
        Api.getService().getProjectList(
                KeyManager.getToken()
        ).enqueue(new Callback<ProjectModel>() {
            @Override
            public void onResponse(Call<ProjectModel> call, Response<ProjectModel> response) {
                switch (response.code()){
                    case 200:
                        adapter.addAll(response.body().projects);
                        break;
                    default:
                        Toast.makeText(getBaseContext(), "에러", Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<ProjectModel> call, Throwable t) {

            }
        });
    }

}
