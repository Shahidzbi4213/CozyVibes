package com.gulehri.edu.pk.mildvideos.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gulehri.edu.pk.mildvideos.databinding.SingleCategoryBinding;
import com.gulehri.edu.pk.mildvideos.model.Categories;
import com.gulehri.edu.pk.mildvideos.ui.CSelectedActivity;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Shahid Iqbal on 12,November,2021
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private final Context mContext;

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(SingleCategoryBinding.inflate(LayoutInflater.from(mContext), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(Categories.CATEGORIES[position].getUrl() + "?auto=compress&cs=tinysrgb&h=350")
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(1, 2)))
                .into(holder.binding.ctBG);

        holder.binding.ctName.setText(Categories.CATEGORIES[position].getName());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, CSelectedActivity.class);
            intent.putExtra("cName", Categories.CATEGORIES[position].getName());
            mContext.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return Categories.CATEGORIES.length;
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {
        private final SingleCategoryBinding binding;

        public CategoryHolder(@NonNull SingleCategoryBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
