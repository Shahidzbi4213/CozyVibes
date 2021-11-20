package com.gulehri.edu.pk.mildvideos.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.gulehri.edu.pk.mildvideos.databinding.SingleItemPopularBinding;
import com.gulehri.edu.pk.mildvideos.model.Model;
import com.gulehri.edu.pk.mildvideos.ui.SliderActivity;

import java.util.ArrayList;

/**
 * Created by Shahid Iqbal on 10,November,2021
 */
public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.DataHolder> {

    private final Context mContext;
    private final ArrayList<Model> videoList;

    public PopularAdapter(Context mContext, ArrayList<Model> videoList) {
        this.mContext = mContext;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DataHolder(SingleItemPopularBinding.inflate(LayoutInflater.from(mContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .thumbnail(Glide.with(mContext).asBitmap().load(videoList.get(position).getThumbnailUrl()))
                .load(videoList.get(position).getVideoUrl())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Bitmap> target,
                                                boolean isFirstResource) {

                        Glide.with(mContext)
                                .asBitmap()
                                .load(videoList.get(position).getThumbnailUrl())
                                .into(holder.binding.thumb);
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource,
                                                   Object model,
                                                   Target<Bitmap> target,
                                                   DataSource dataSource,
                                                   boolean isFirstResource) {
                        holder.binding.thumb.setImageBitmap(resource);
                        return true;
                    }
                })
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

    public static class DataHolder extends RecyclerView.ViewHolder {
        private final SingleItemPopularBinding binding;

        public DataHolder(@NonNull SingleItemPopularBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
