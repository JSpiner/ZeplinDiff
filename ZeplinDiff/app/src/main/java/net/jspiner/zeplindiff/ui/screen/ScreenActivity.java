package net.jspiner.zeplindiff.ui.screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;

import net.jspiner.zeplindiff.R;
import net.jspiner.zeplindiff.databinding.ActivityProjectsBinding;
import net.jspiner.zeplindiff.model.Screen;
import net.jspiner.zeplindiff.ui.base.BaseActivity;

import java.util.ArrayList;

public class ScreenActivity extends BaseActivity<ActivityProjectsBinding, Contract.Presenter> implements Contract.View {

    private static final int PROJECT_LIST_GRID_SPAN_COUNT = 2;

    private ScreenAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_projects;
    }

    @Override
    protected Contract.Presenter createPresenter() {
        return new ScreenPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        adapter = new ScreenAdapter();
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, PROJECT_LIST_GRID_SPAN_COUNT));
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void addScreenList(ArrayList<Screen> screenList) {
        adapter.addAll(screenList);
    }

    @Override
    public String getProjectId() {
        return getIntent().getStringExtra("hash_id");
    }
}
