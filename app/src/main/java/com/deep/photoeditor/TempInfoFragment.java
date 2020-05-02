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
public class TempInfoFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<TempInfo> lstTempInfo;


    public TempInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tempinfo, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.tempInfo_recyclerView);
        RecyclerViewAdapter_tempInfo recyclerViewAdapter = new RecyclerViewAdapter_tempInfo(getContext(),lstTempInfo);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstTempInfo = new ArrayList<>();
        lstTempInfo.add(new TempInfo("Idot Dog",R.drawable.meme1));
        lstTempInfo.add(new TempInfo("cute dog",R.drawable.meme2));
        lstTempInfo.add(new TempInfo("cat",R.drawable.meme3));
        lstTempInfo.add(new TempInfo("Idot star",R.drawable.meme4));
        lstTempInfo.add(new TempInfo("morning flower",R.drawable.meme5));
        lstTempInfo.add(new TempInfo("my idol",R.drawable.meme6));

    }
}
