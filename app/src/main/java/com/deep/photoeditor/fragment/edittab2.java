package com.deep.photoeditor.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deep.photoeditor.R;
import com.deep.photoeditor.adpater.RecyclerViewAdapter_mineTemp;
import com.deep.photoeditor.mineTemplate;

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
        lstmineTemplate.add(new mineTemplate("曾之喬","https://www.wepeople.club/new-wepeople-upload/bb3886dc4b408776b51b1ba18ad3fb1e.jpg","Angela",20));
        lstmineTemplate.add(new mineTemplate("Winnie with Pooh","https://www.trafalgar.com/real-word/wp-content/uploads/sites/3/2020/01/Screenshot-2020-01-16-at-15.15.54.png","Angela",12));
        lstmineTemplate.add(new mineTemplate("しばいぬ","https://assets.juksy.com/files/articles/90814/800x_100_w-5ce41f432bb5d.jpg","Angela",35));
        lstmineTemplate.add(new mineTemplate("蠟筆小新","https://cdn2.ettoday.net/images/4705/4705033.jpg","Angela",23));
        lstmineTemplate.add(new mineTemplate("ねずこ","https://i.ytimg.com/vi/wqGSbiLC3aA/maxresdefault.jpg","Angela",38));
        lstmineTemplate.add(new mineTemplate("我妻善逸","https://i.ytimg.com/vi/U72gIwh9jr0/maxresdefault.jpg","Angela",25));

    }

}
