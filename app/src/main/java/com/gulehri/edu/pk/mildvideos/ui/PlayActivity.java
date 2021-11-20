package com.gulehri.edu.pk.mildvideos.ui;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gulehri.edu.pk.mildvideos.R;
import com.gulehri.edu.pk.mildvideos.databinding.ActivityPlayBinding;
import com.gulehri.edu.pk.mildvideos.model.Model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

    private ActivityPlayBinding binding;
    private ArrayList<Model> videoList;
    private int position;
    private boolean fullScreen;
    private ArrayList<MediaItem> mediaList;
    private String title;
    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityPlayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getData();
        setPlayer();
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void setPlayer() {
        player = new ExoPlayer.Builder(this).build();
        binding.playerView.setPlayer(player);
        binding.playerView.setShowNextButton(true);

        // Build the media item.
        mediaList = new ArrayList<>();
        for (int i = position; i < videoList.size(); i++) {
            mediaList.add(MediaItem.fromUri(videoList.get(i).getVideoUrlHD()));

        }

        for (int j = 0; j < mediaList.size(); j++) {
            player.addMediaItem(mediaList.get(j));
        }


        findViewById(R.id.btn_bck).setOnClickListener(v -> onBackPressed());

        TextView textView = findViewById(R.id.tvName);
        title = videoList.get(position).getVideoTitle();
        title = title.substring(0, 1).toUpperCase() + title.substring(1);
        textView.setText(title);

        player.addListener(new Player.Listener() {
            @Override
            public void onMediaItemTransition(@Nullable MediaItem mediaItem, int reason) {
                position++;
                title = videoList.get(position).getVideoTitle();
                title = title.substring(0, 1).toUpperCase() + title.substring(1);
                textView.setText(title);
            }
        });


        player.prepare();
        player.play();


        final ImageView fullIco = findViewById(R.id.exo_fullscreen_icon);

        fullIco.setOnClickListener(v -> {

            if (!fullScreen) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                fullIco.setImageDrawable(ContextCompat.
                        getDrawable(this, R.drawable.exo_ic_fullscreen_exit));
                fullScreen = true;
            } else {
                fullIco.setImageDrawable(ContextCompat.
                        getDrawable(this, R.drawable.exo_ic_fullscreen_enter));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                fullScreen = false;
            }

        });


    }

    private void getData() {
        position = getIntent().getIntExtra("position", 0);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Model>>() {
        }.getType();
        videoList = gson.fromJson(getIntent().getStringExtra("list"), type);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    private void clearData() {
        player.release();
        player.clearMediaItems();
        binding.playerView.removeAllViews();
        videoList.clear();
        mediaList.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearData();

    }
}