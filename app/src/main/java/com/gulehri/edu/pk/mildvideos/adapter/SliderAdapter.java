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
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.gulehri.edu.pk.mildvideos.databinding.SliderItemsBinding;
import com.gulehri.edu.pk.mildvideos.model.Model;
import com.gulehri.edu.pk.mildvideos.ui.PlayActivity;

import java.util.ArrayList;

/**
 * Created by Shahid Iqbal on 10,November,2021
 */
public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SlidesHolder> {

    private final Context mContext;
    private final int id;
    private final ArrayList<Model> tempList = new ArrayList<>();

    public SliderAdapter(Context mContext, int id, ArrayList<Model> videosList) {
        this.mContext = mContext;
        this.id = id;
        setData(videosList);
    }

    private void setData(@NonNull ArrayList<Model> list) {

        for (int i = id; i < list.size(); i++) {
            tempList.add(list.get(i));
        }
    }

    @NonNull
    @Override
    public SliderAdapter.SlidesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SlidesHolder(SliderItemsBinding.inflate(LayoutInflater.from(mContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter.SlidesHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .thumbnail(Glide.with(mContext).asBitmap().load(tempList.get(position).getThumbnailUrl()))
                .load(tempList.get(position).getVideoUrl())
                .apply(new RequestOptions().override(300, 300).centerCrop())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Glide.with(mContext).asBitmap().load(tempList.get(position).getThumbnailUrl()).into(holder.binding.thumbing);
                        return true;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        holder.binding.thumbing.setImageResource(0);
                        holder.binding.thumbing.setImageBitmap(resource);
                        return false;
                    }
                })
                .into(holder.binding.thumbing);

        holder.itemView.setOnClickListener(v -> {

            Gson gson = new Gson();
            Intent intent = new Intent(mContext, PlayActivity.class);
            intent.putExtra("list", gson.toJson(tempList));
            intent.putExtra("position", position);
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return tempList.size();
    }

    public static class SlidesHolder extends RecyclerView.ViewHolder {
        private final SliderItemsBinding binding;

        public SlidesHolder(@NonNull SliderItemsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
