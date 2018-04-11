package net.jspiner.zeplindiff.ui.project;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;

import net.jspiner.zeplindiff.R;
import net.jspiner.zeplindiff.databinding.ActivityProjectsBinding;
import net.jspiner.zeplindiff.model.Project;
import net.jspiner.zeplindiff.ui.base.BaseActivity;

import java.util.ArrayList;

public class ProjectActivity extends BaseActivity<ActivityProjectsBinding, Contract.Presenter> implements Contract.View {

    private static final int PROJECT_LIST_GRID_SPAN_COUNT = 2;
    private ProjectAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_projects;
    }

    @Override
    protected Contract.Presenter createPresenter() {
        return new ProjectPresenter(this);
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
        adapter = new ProjectAdapter();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, PROJECT_LIST_GRID_SPAN_COUNT));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void addProjectList(ArrayList<Project> projectList) {
        adapter.addAll(projectList);
    }
}
