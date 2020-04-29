package com.deep.photoeditor;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class maintab2 extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<elderTemplate> lstelderTemplate;


    public maintab2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_maintab2, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.elderTemplate_recyclerView);
        RecyclerViewAdapter__elderTemp recyclerViewAdapter_elderTemp = new RecyclerViewAdapter__elderTemp(getContext(), lstelderTemplate);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter_elderTemp);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstelderTemplate = new ArrayList<>();
        lstelderTemplate.add(new elderTemplate("百合花",R.drawable.elder1));
        lstelderTemplate.add(new elderTemplate("一支百合花",R.drawable.elder2));
        lstelderTemplate.add(new elderTemplate("海浪與夕陽",R.drawable.elder9));
        lstelderTemplate.add(new elderTemplate("海岸",R.drawable.elder10));
        lstelderTemplate.add(new elderTemplate("盛開的百合花",R.drawable.elder3));
        lstelderTemplate.add(new elderTemplate("白色百合花",R.drawable.elder4));
        lstelderTemplate.add(new elderTemplate("書與玫瑰",R.drawable.elder5));
        lstelderTemplate.add(new elderTemplate("綠樹遮天",R.drawable.elder6));
        lstelderTemplate.add(new elderTemplate("秋天瓢蟲",R.drawable.elder7));
        lstelderTemplate.add(new elderTemplate("露珠_湖面",R.drawable.elder8));

    }

}
