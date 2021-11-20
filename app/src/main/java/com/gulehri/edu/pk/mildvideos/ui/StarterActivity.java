package com.gulehri.edu.pk.mildvideos.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.gulehri.edu.pk.mildvideos.databinding.ActivityStarterBinding;
import com.gulehri.edu.pk.mildvideos.model.Quotes;
import com.gulehri.edu.pk.mildvideos.utils.DataSaver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class StarterActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        com.gulehri.edu.pk.mildvideos.databinding.ActivityStarterBinding binding = ActivityStarterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayList<Quotes> quotes = new ArrayList<>(Arrays.asList(Quotes.QUOTES));
        Collections.shuffle(quotes);

        DataSaver saver = new DataSaver(this);
        int position = saver.getPosition();
        binding.tvQuotes.setText(quotes.get(position).getQuote());
        binding.tvAuthur.setText("―" + quotes.get(position).getAurthur());

        if (position > Quotes.QUOTES.length) {
            position = 0;
        } else {
            position += 1;
        }

        saver.savePosition(position);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(StarterActivity.this, MainActivity.class));
            finish();
        }, 4000);
    }
}