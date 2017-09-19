package net.jspiner.zeplindiff.screen;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import net.jspiner.zeplindiff.R;
import net.jspiner.zeplindiff.model.Project;
import net.jspiner.zeplindiff.model.Screen;
import net.jspiner.zeplindiff.project.ProjectViewHolder;

import java.util.ArrayList;

public class ScreenAdapter extends RecyclerView.Adapter<ScreenViewHolder> {

    private ArrayList<Screen> projectList = new ArrayList<>();

    @Override
    public ScreenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ScreenViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.card_screen,
                        parent,
                        false
                )
        );
    }

    public void addAll(ArrayList arrayList){
        projectList.addAll(arrayList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ScreenViewHolder holder, int position) {
        holder.setData(
                projectList.get(position)
        );
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }
}
