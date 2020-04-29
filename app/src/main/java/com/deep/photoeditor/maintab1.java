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
public class maintab1 extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<memeTemplate> lstMemeMemeTemplate;


    public maintab1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_maintab1, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.memeTemplate_recyclerView);
        RecyclerViewAdapter__memeTemp recyclerViewAdapter = new RecyclerViewAdapter__memeTemp(getContext(), lstMemeMemeTemplate);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstMemeMemeTemplate = new ArrayList<>();
        lstMemeMemeTemplate.add(new memeTemplate("Idot Dog",R.drawable.meme1));
        lstMemeMemeTemplate.add(new memeTemplate("cute dog",R.drawable.meme2));
        lstMemeMemeTemplate.add(new memeTemplate("cat",R.drawable.meme3));
        lstMemeMemeTemplate.add(new memeTemplate("Idot star",R.drawable.meme4));
        lstMemeMemeTemplate.add(new memeTemplate("morning flower",R.drawable.meme5));
        lstMemeMemeTemplate.add(new memeTemplate("my idol",R.drawable.meme6));
        lstMemeMemeTemplate.add(new memeTemplate("Idot",R.drawable.meme7));
        lstMemeMemeTemplate.add(new memeTemplate("Winnie with Pooh",R.drawable.meme8));
        lstMemeMemeTemplate.add(new memeTemplate("しばいぬ",R.drawable.meme9));
        lstMemeMemeTemplate.add(new memeTemplate("蠟筆小新長大",R.drawable.meme10));
        lstMemeMemeTemplate.add(new memeTemplate("ねずこ",R.drawable.meme11));
        lstMemeMemeTemplate.add(new memeTemplate("我妻善逸",R.drawable.meme12));

    }
}
