package net.jspiner.zeplindiff.project;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;

import net.jspiner.zeplindiff.databinding.CardProjectBinding;
import net.jspiner.zeplindiff.model.Project;
import net.jspiner.zeplindiff.screen.ScreenActivity;

public class ProjectViewHolder extends RecyclerView.ViewHolder {

    CardProjectBinding binding;

    public ProjectViewHolder(final ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = (CardProjectBinding) binding;
    }

    public void setData(final Project project){
        Glide.with(binding.getRoot().getContext())
                .load(project.thumbnail)
                .centerCrop()
                .into(binding.image);
        binding.projectName.setText(project.name);

        this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(binding.getRoot().getContext(), ScreenActivity.class);
                intent.putExtra("hash_id", project._id);
                binding.getRoot().getContext().startActivity(intent);
            }
        });
    }

}
