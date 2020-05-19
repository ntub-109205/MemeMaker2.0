package com.deep.photoeditor.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.deep.photoeditor.PublicMeme;
import com.deep.photoeditor.R;
import com.deep.photoeditor.adpater.RecyclerViewAdapter__meme;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TempInfoFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<PublicMeme> lstMemeInfo;


    public TempInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tempinfo, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.tempInfo_recyclerView);
        RecyclerViewAdapter__meme recyclerViewAdapter = new RecyclerViewAdapter__meme(getContext(),lstMemeInfo);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstMemeInfo = new ArrayList<>();
        lstMemeInfo.add(new PublicMeme("#居家隔離","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1585196981888.jpg","oUO",30));
        lstMemeInfo.add(new PublicMeme("#牽手","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1584282936597.jpg","天才",20));
        lstMemeInfo.add(new PublicMeme("#期末考","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1578233253954.jpg","龍澳天本添",9));
        lstMemeInfo.add(new PublicMeme("#快去睡","https://memeprod.s3.ap-northeast-1.amazonaws.com/user-wtf/1577726914062.jpg","七彩瑪莉蘇",6));
//        lstTempInfo.add(new TempInfo("morning flower",R.drawable.meme5));
//        lstTempInfo.add(new TempInfo("my idol",R.drawable.meme6));

    }
}
