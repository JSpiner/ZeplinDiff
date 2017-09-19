package net.jspiner.zeplindiff.screen;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;

import net.jspiner.zeplindiff.databinding.CardProjectBinding;
import net.jspiner.zeplindiff.databinding.CardScreenBinding;
import net.jspiner.zeplindiff.model.Project;
import net.jspiner.zeplindiff.model.Screen;

public class ScreenViewHolder extends RecyclerView.ViewHolder {

    CardScreenBinding binding;

    public ScreenViewHolder(final ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = (CardScreenBinding) binding;

        this.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(binding.getRoot().getContext(), ScreenActivity.class);
                binding.getRoot().getContext().startActivity(intent);
            }
        });
    }

    public void setData(Screen screen){
        Glide.with(binding.getRoot().getContext())
                .load(screen.latestVersion.snapshot.url)
                .centerCrop()
                .into(binding.image);
        binding.projectName.setText(screen.name);
    }

}
