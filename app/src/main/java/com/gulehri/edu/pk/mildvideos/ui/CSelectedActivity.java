package com.gulehri.edu.pk.mildvideos.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gulehri.edu.pk.mildvideos.adapter.SelectCategoryAdapter;
import com.gulehri.edu.pk.mildvideos.databinding.ActivityCselectedBinding;
import com.gulehri.edu.pk.mildvideos.model.Model;
import com.gulehri.edu.pk.mildvideos.utils.CheckConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CSelectedActivity extends AppCompatActivity {

    private ActivityCselectedBinding binding;
    private ArrayList<Model> videosList;
    private boolean isScrolling;
    private SelectCategoryAdapter adapter;
    private int currentItems, totalItems, scrollOutItems, pageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCselectedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setToolBar();
        setAdapter();
        fetchVideos();
    }

    private void setToolBar() {
        setSupportActionBar(binding.csToolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.tvCName.setText(selectedCategory());
    }

    private String selectedCategory() {
        return getIntent().getStringExtra("cName");
    }


    private void setAdapter() {
        videosList = new ArrayList<>();
        adapter = new SelectCategoryAdapter(this, videosList);
        binding.selectedViewer.setAdapter(adapter);
        binding.selectedViewer.clearAnimation();
        binding.selectedViewer.setItemAnimator(null);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        binding.selectedViewer.setLayoutManager(gridLayoutManager);

        binding.selectedViewer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    currentItems = gridLayoutManager.getChildCount();
                    totalItems = gridLayoutManager.getItemCount();
                    scrollOutItems = gridLayoutManager.findFirstVisibleItemPosition();
                }

                if (isScrolling && currentItems + scrollOutItems >= totalItems) {
                    isScrolling = false;
                    fetchVideos();
                }
            }
        });
    }

    private void fetchVideos() {
        CheckConnection connection = new CheckConnection(this);
        if (connection.haveNetworkConnection()) {
            final String url = "https://api.pexels.com/videos/search?query=" + selectedCategory() + "&per_page=60" + "&page=" + pageNumber;
            StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("videos");
                    int len = array.length();

                    for (int i = 0; i < len; i++) {

                        final JSONObject object = array.getJSONObject(i);
                        final int id = object.getInt("id");
                        String videoTitle = object.getString("url");

                        if ((videoTitle.contains(String.valueOf(id))) && (videoTitle.contains("https://www.pexels.com/video/"))) {
                            videoTitle = videoTitle.replaceAll(String.valueOf(id), "");
                            videoTitle = videoTitle.replaceAll("https://www.pexels.com/video/", "");
                            videoTitle = videoTitle.replaceAll("-", " ");
                            videoTitle = videoTitle.replaceAll("/", "");
                        }


                        JSONObject user = object.getJSONObject("user");
                        final String userName = user.getString("name");

                        JSONArray videoFiles = object.getJSONArray("video_files");
                        JSONObject objVideo = videoFiles.getJSONObject(0);
                        JSONObject objHD = videoFiles.getJSONObject(2);
                        final String videoLink = objVideo.getString("link");
                        final String videoLinkHD = objHD.getString("link");

                        JSONArray videosPicture = object.getJSONArray("video_pictures");
                        JSONObject objPic = videosPicture.getJSONObject(0);
                        final String picUrl = objPic.getString("picture");


                        Model model = new Model(id, userName, videoLink, videoLinkHD,videoTitle, picUrl);
                        videosList.add(model);
                    }
                    adapter.notifyDataSetChanged();
                    pageNumber += 1;

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }, error -> {

            }) {
                //Setting Header and Passing Over API Key without that API request won't work
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> map = new HashMap<>();
                    map.put("Authorization", "563492ad6f917000010000013c35869795db4034972b1408c54283c4");
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } else {
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        videosList.clear();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        videosList.clear();
    }
}