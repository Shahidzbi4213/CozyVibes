package com.gulehri.edu.pk.mildvideos.ui;

import static android.app.DownloadManager.Request.NETWORK_MOBILE;
import static android.app.DownloadManager.Request.NETWORK_WIFI;
import static android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gulehri.edu.pk.mildvideos.adapter.SliderAdapter;
import com.gulehri.edu.pk.mildvideos.databinding.ActivitySliderBinding;
import com.gulehri.edu.pk.mildvideos.model.Model;
import com.gulehri.edu.pk.mildvideos.utils.CheckConnection;
import com.jackandphantom.carouselrecyclerview.CarouselLayoutManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class SliderActivity extends AppCompatActivity {

    private ActivitySliderBinding binding;
    private ArrayList<Model> videosList;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySliderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.floatingBtn.setOnClickListener(v -> downloadVideo());

        getData();
        setAspectRatio();
        setResources(position);
        setSlider();

    }

    private void setSlider() {
        try {
            final SliderAdapter adapter = new SliderAdapter(this, position, videosList);
            binding.imageSlider.setAdapter(adapter);
            binding.imageSlider.set3DItem(true);
            binding.imageSlider.setAlpha(true);
            binding.imageSlider.setIntervalRatio(0.5f);

            binding.imageSlider.setItemSelectListener(new CarouselLayoutManager.OnSelected() {
                @Override
                public void onItemSelected(int i) {
                    setResources(position + i);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAspectRatio() {
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        float ratio = ((float) metrics.heightPixels / (float) metrics.widthPixels);
        binding.imageView.setRatio(ratio);
    }

    private void setResources(int pos) {

        Glide.with(this)
                .asBitmap()
                .load(videosList.get(pos).getThumbnailUrl())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(25, 3)))
                .into(binding.imageView);

        binding.photographerName.setText(videosList.get(pos).getUserName());
    }

    private void getData() {
        Intent intent = getIntent();
        String str = intent.getStringExtra("list");
        position = intent.getIntExtra("position", 0);
        Gson gson = new Gson();

        Type type = new TypeToken<List<Model>>() {
        }.getType();
        videosList = gson.fromJson(str, type);

    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(SliderActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            downloadVideo();

    }


    private void downloadVideo() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (new CheckConnection(this).haveNetworkConnection()) {
                Uri uri = Uri.parse(videosList.get(binding.imageSlider.getSelectedPosition() + position).getVideoUrl());
                DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setAllowedNetworkTypes(NETWORK_WIFI | NETWORK_MOBILE);
                request.setTitle(videosList.get(position + binding.imageSlider.getSelectedPosition()).getVideoTitle() + ".mp4");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, videosList.get(position + binding.imageSlider.getSelectedPosition()).getVideoTitle() + ".mp4");
                downloadManager.enqueue(request);
            } else {
                Toast.makeText(this, "Check internet connection", Toast.LENGTH_SHORT).show();
            }

        } else {
            requestPermission();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}