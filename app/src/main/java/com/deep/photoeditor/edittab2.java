package com.deep.photoeditor;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
public class edittab2 extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<mineTemplate> lstmineTemplate;


    public edittab2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_edittab2, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.mineTemplate_recyclerView);
        RecyclerViewAdapter_mineTemp recyclerViewAdapter_mineTemp = new RecyclerViewAdapter_mineTemp(getContext(),lstmineTemplate);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter_mineTemp);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstmineTemplate = new ArrayList<>();
        lstmineTemplate.add(new mineTemplate("Idot",R.drawable.meme7));
        lstmineTemplate.add(new mineTemplate("Winnie with Pooh",R.drawable.meme8));
        lstmineTemplate.add(new mineTemplate("しばいぬ",R.drawable.meme9));
        lstmineTemplate.add(new mineTemplate("蠟筆小新長大",R.drawable.meme10));
        lstmineTemplate.add(new mineTemplate("ねずこ",R.drawable.meme11));
        lstmineTemplate.add(new mineTemplate("我妻善逸",R.drawable.meme12));

    }

}
