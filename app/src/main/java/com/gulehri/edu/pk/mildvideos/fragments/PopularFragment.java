package com.gulehri.edu.pk.mildvideos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gulehri.edu.pk.mildvideos.R;
import com.gulehri.edu.pk.mildvideos.adapter.PopularAdapter;
import com.gulehri.edu.pk.mildvideos.model.Model;
import com.gulehri.edu.pk.mildvideos.utils.CheckConnection;
import com.gulehri.edu.pk.mildvideos.utils.DataSaver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PopularFragment extends Fragment {

    private int pageNumber = 1;
    private ArrayList<Model> videosList;
    private PopularAdapter adapter;
    private boolean isScrolling = false;
    private int currentItems = 0;
    private int totalItems = 0;
    private int scrollOutItems = 0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setAdapter();
        fetchVideos();
    }

    private void fetchVideos() {
        DataSaver saver = new DataSaver(requireContext());
        String searchedValue = saver.getData();
        CheckConnection connection = new CheckConnection(this.getContext());
        if (connection.haveNetworkConnection()) {
            String url;
            if (searchedValue != null && searchedValue.length() > 0) {
                url = "https://api.pexels.com/videos/search?query=" + searchedValue + "&per_page=50" + "&page=" + pageNumber;
            } else {
                url = "https://api.pexels.com/videos/popular?&per_page=50" + "&page=" + pageNumber;
            }
            //Setting Header and Passing Over API Key without that API request won't work
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
                        JSONObject objVideo = videoFiles.getJSONObject(1);
                        JSONObject objHD = videoFiles.getJSONObject(2);

                        final String videoLink = objVideo.getString("link");
                        final String videoLinkHD = objHD.getString("link");

                        JSONArray videosPicture = object.getJSONArray("video_pictures");
                        JSONObject objPic = videosPicture.getJSONObject(0);
                        final String picUrl = objPic.getString("picture");

                        Model model = new Model(id, userName, videoLink, videoLinkHD, videoTitle, picUrl);
                        videosList.add(model);
                    }
                    adapter.notifyDataSetChanged();
                    pageNumber += 1;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, Throwable::printStackTrace) {
                //Setting Header and Passing Over API Key without that API request won't work
                @NonNull
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> map = new HashMap<>();
                    map.put("Authorization", "563492ad6f917000010000013c35869795db4034972b1408c54283c4");
                    return map;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);


        } else {
            Toast.makeText(getContext(), "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter() {
        RecyclerView recyclerView = PopularFragment.this.requireView().findViewById(R.id.popularViewer);
        videosList = new ArrayList<>();
        adapter = new PopularAdapter(requireContext(), videosList);
        recyclerView.clearAnimation();
        recyclerView.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

}