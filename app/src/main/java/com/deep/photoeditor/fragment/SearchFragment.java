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
import com.deep.photoeditor.adpater.RecyclerViewAdapter_tagSearch;
import com.deep.photoeditor.api;
import com.deep.photoeditor.tagSearch;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    View v;
    private RecyclerView myrecyclerview;
    private List<tagSearch> lstTagSearch;

    //api
    private static api callApi = new api();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_search, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.tagRecyclerview);
        RecyclerViewAdapter_tagSearch recyclerViewAdapter = new RecyclerViewAdapter_tagSearch(getContext(),lstTagSearch);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstTagSearch = new ArrayList<>();
        lstTagSearch.add(new tagSearch("#居家隔離"));
        lstTagSearch.add(new tagSearch("#牽手"));
        lstTagSearch.add(new tagSearch("#期末考"));
        lstTagSearch.add(new tagSearch("#快去睡"));
        lstTagSearch.add(new tagSearch("#美國隊長"));
        lstTagSearch.add(new tagSearch("#曾之喬"));
        lstTagSearch.add(new tagSearch("#實習"));
        lstTagSearch.add(new tagSearch("#Travel"));
        lstTagSearch.add(new tagSearch("#小琉球"));
        lstTagSearch.add(new tagSearch("#自信"));
    }

}
