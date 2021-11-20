package com.gulehri.edu.pk.mildvideos.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gulehri.edu.pk.mildvideos.R;
import com.gulehri.edu.pk.mildvideos.adapter.CategoryAdapter;

public class CategoryFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = view.findViewById(R.id.categoryViewer);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        recyclerView.setAdapter(new CategoryAdapter(view.getContext()));
        recyclerView.setItemAnimator(null);
        recyclerView.clearAnimation();
    }
}