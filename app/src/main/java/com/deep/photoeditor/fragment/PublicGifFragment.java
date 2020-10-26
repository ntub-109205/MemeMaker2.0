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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublicGifFragment extends Fragment {

    View v;
    private RecyclerView myrecyclerview;
    private List<PublicMeme> lstMemeMeme;

    public PublicGifFragment() {
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
        lstMemeMeme.add(new PublicMeme("1","1","#彩虹貓","https://www.urad.com.tw/wp-content/uploads/2015/08/giphy.gif","jessie",30,1));
        lstMemeMeme.add(new PublicMeme("1","1","#地球","https://upload.wikimedia.org/wikipedia/commons/2/2c/Rotating_earth_%28large%29.gif","Geo",20,1));
        lstMemeMeme.add(new PublicMeme("1","1","#早安","https://i.pinimg.com/originals/87/40/f7/8740f719676a6393b9acb80e1f99b97f.gif","跑跑跑的人",19,0));
        lstMemeMeme.add(new PublicMeme("1","1","#孫儷","https://i1.kknews.cc/SIG=ko35sv/39o90003725ro568no98.jpg","好累",18,0));
        lstMemeMeme.add(new PublicMeme("1","1","#0元特效","https://i3.read01.com/SIG=64obee/304c6a336773744e6265.jpg","Anc1233",17,0));
        lstMemeMeme.add(new PublicMeme("1","1","#迪麗熱巴","https://i2.kknews.cc/SIG=2pfm603/39oq0001ps2s2ro7ns97.jpg","牙醫09",16,1));
        lstMemeMeme.add(new PublicMeme("1","1","#剛穿新鞋的你","https://i3.read01.com/SIG=1crbr31/304c6a33677341366b4d.jpg","江戶川先生",14,0));


    }
}
