package com.gulehri.edu.pk.mildvideos.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gulehri.edu.pk.mildvideos.R;
import com.gulehri.edu.pk.mildvideos.databinding.SingleItemPopularBinding;
import com.gulehri.edu.pk.mildvideos.model.Model;
import com.gulehri.edu.pk.mildvideos.ui.SliderActivity;

import java.util.ArrayList;

/**
 * Created by Shahid Iqbal on 12,November,2021
 */
public class SelectCategoryAdapter extends RecyclerView.Adapter<SelectCategoryAdapter.Selector> {
    private final Context mContext;
    private final ArrayList<Model> videoList;

    public SelectCategoryAdapter(Context mContext, ArrayList<Model> videoList) {
        this.mContext = mContext;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public SelectCategoryAdapter.Selector onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Selector(SingleItemPopularBinding.inflate(LayoutInflater.from(mContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SelectCategoryAdapter.Selector holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .thumbnail(Glide.with(mContext).asBitmap().load(videoList.get(position).getThumbnailUrl()))
                .load(videoList.get(position).getThumbnailUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.binding.thumb);

        holder.itemView.setOnClickListener(v -> {
            Gson gson = new Gson();
            Intent intent = new Intent(mContext, SliderActivity.class);
            intent.putExtra("position", position);
            intent.putExtra("list", gson.toJson(videoList));
            mContext.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public static class Selector extends RecyclerView.ViewHolder {
        private final SingleItemPopularBinding binding;

        public Selector(@NonNull SingleItemPopularBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
