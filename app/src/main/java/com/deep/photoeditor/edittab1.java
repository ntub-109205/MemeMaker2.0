package com.deep.photoeditor;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class edittab1 extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<hotTemplate> lsthotTemplate;


    public edittab1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_edittab1, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.hotTemplate_recyclerView);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(),lsthotTemplate);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lsthotTemplate = new ArrayList<>();
        lsthotTemplate.add(new hotTemplate("Idot Dog",R.drawable.meme1));
        lsthotTemplate.add(new hotTemplate("cute dog",R.drawable.meme2));
        lsthotTemplate.add(new hotTemplate("cat",R.drawable.meme3));
        lsthotTemplate.add(new hotTemplate("Idot star",R.drawable.meme4));
        lsthotTemplate.add(new hotTemplate("morning flower",R.drawable.meme5));
        lsthotTemplate.add(new hotTemplate("my idol",R.drawable.meme6));

    }
}
