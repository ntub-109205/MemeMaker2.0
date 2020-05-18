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
public class PublicMMFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<PublicMeme> lstMemeMeme;

    public PublicMMFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_public_tab1, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.publicMeme_recyclerView);
        RecyclerViewAdapter__meme recyclerViewAdapter = new RecyclerViewAdapter__meme(getContext(), lstMemeMeme);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lstMemeMeme = new ArrayList<>();
        lstMemeMeme.add(new PublicMeme("#Dog #隔離","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1585196981888.jpg","潔西卡",35));
        lstMemeMeme.add(new PublicMeme("#反叛的魯路修","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1589009676654.jpg","Geo",20));
        lstMemeMeme.add(new PublicMeme("#三國殺","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1589044855818.jpg","跑跑跑的人",19));
        lstMemeMeme.add(new PublicMeme("#鼠定了你","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1589086291871.jpg","好累",18));
        lstMemeMeme.add(new PublicMeme("#某系列主機遊戲","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1589041392638.jpg","Anc1233",17));
        lstMemeMeme.add(new PublicMeme("#PM日常","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1589041053097.jpg","牙醫09",16));
        lstMemeMeme.add(new PublicMeme("#歷史","https://memeprod.sgp1.digitaloceanspaces.com/user-wtf/1589034990886.jpg","江戶川先生",14));


    }
}
