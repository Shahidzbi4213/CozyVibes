package com.gulehri.edu.pk.mildvideos.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class DataSaver {

    private final SharedPreferences sp;
    private final SharedPreferences.Editor editor;

    public DataSaver(Context context) {

        sp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public void saveData(String data) {
        editor.putString("key", data);
        editor.apply();
    }

    public void savePosition(int position) {
        editor.putInt("pos", position);
        editor.apply();
    }

    public int getPosition() {

        return sp.getInt("pos", 0);
    }

    public String getData() {
        return sp.getString("key", null);
    }
}
