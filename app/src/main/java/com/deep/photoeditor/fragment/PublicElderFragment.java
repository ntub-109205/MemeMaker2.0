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
import com.deep.photoeditor.adpater.RecyclerViewAdapter__gif;
import com.deep.photoeditor.adpater.RecyclerViewAdapter__meme;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublicElderFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<PublicMeme> lstMemeMeme;

    public PublicElderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_public_tab1, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.publicMeme_recyclerView);
        RecyclerViewAdapter__gif recyclerViewAdapter = new RecyclerViewAdapter__gif(getContext(), lstMemeMeme);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstMemeMeme = new ArrayList<>();
        lstMemeMeme.add(new PublicMeme("#早安","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ30pqBnXi_TeFAw6HRq5Fuj7Gn87UDy5HlMyaor_hbrJwU6c4W&usqp=CAU","jessie",30));
        lstMemeMeme.add(new PublicMeme("#午安","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRdurKHXqRX6Sf7jZ5wA95BtGmF67r4RMrfcyHyyhwUTn9rdnSx&usqp=CAU","Geo",20));
        lstMemeMeme.add(new PublicMeme("#沒錢 #努力","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcR7pZhRXjwG6fRsLIMKOuvpCVTQ1lkG_ppoEgUTf5PEUCaUokVy&usqp=CAU","跑跑跑的人",19));
        lstMemeMeme.add(new PublicMeme("#周末愉快","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ9u3tpzS-PsSYdZ9qJdi_qsnvOCdfzKTy5YfgpgYpkqAT4vOLp&usqp=CAU","好累",18));
        lstMemeMeme.add(new PublicMeme("#早安","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQ31J8IdHmgv-A5K7R1HS6DsRu8juT6uHdPcqLDbgn_NAd58Afa&usqp=CAU","Anc1233",17));
        lstMemeMeme.add(new PublicMeme("#晚安","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQgTnBFHVPfe260CJsG7UQ6uTopSzsQ47qOmMsVESUmMwPy8Og2&usqp=CAU","牙醫09",16));
        lstMemeMeme.add(new PublicMeme("#認同","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTqJWtzg9D-9omdBRjwYEXHifBGMjcraz7ZwW_AQa29H7eF8pfH&usqp=CAU","江戶川先生",14));


    }
}
