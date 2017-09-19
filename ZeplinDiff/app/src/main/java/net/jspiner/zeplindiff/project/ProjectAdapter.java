package net.jspiner.zeplindiff.project;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import net.jspiner.zeplindiff.R;
import net.jspiner.zeplindiff.model.Project;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectViewHolder> {

    private ArrayList<Project> projectList = new ArrayList<>();

    @Override
    public ProjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ProjectViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.card_project,
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
    public void onBindViewHolder(ProjectViewHolder holder, int position) {
        holder.setData(
                projectList.get(position)
        );
    }

    @Override
    public int getItemCount() {
        return projectList.size();
    }
}
