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
        lstMemeMeme.add(new PublicMeme("1","1","#我真可愛","https://lh3.googleusercontent.com/proxy/_aOVP7bqrBMjyM8izkVPTgzgmueVtmxlD71EBLbFbHI_E6jISa_hl9rsUri0KemCClp3GjuUWeZulFDVyfz50B_s9PerMAX9TXBCpSm_WG0uQ8BSHCmdFd2C","jessie",30,1));
        lstMemeMeme.add(new PublicMeme("1","1","#誰怕誰","https://4.bp.blogspot.com/-cvLAyXhtB3c/XQrc0yQCQ2I/AAAAAAAMlPE/wNN_5eU1Xe0SYainh1NefU-sBIYj8ZksACLcBGAs/s1600/AS0005411_03.gif","Geo",20,1));
        lstMemeMeme.add(new PublicMeme("1","1","#太好了","https://media1.tenor.com/images/200bec2e1191d7e08ee5e7832fd0a1bf/tenor.gif?itemid=11841779","跑跑跑的人",19,0));
        lstMemeMeme.add(new PublicMeme("1","1","#骷顱頭 #CUTE","https://helpx.adobe.com/content/dam/help/en/photoshop/how-to/create-animated-gif/jcr_content/main-pars/image_4389415/create-animated-gif_3a-v2.gif","好累",18,0));
        lstMemeMeme.add(new PublicMeme("1","1","#鬼滅之刃","https://imgur.com/CV5zi8A.gif","Anc1233",17,0));
        lstMemeMeme.add(new PublicMeme("1","1","#迪麗熱巴","https://lh3.googleusercontent.com/proxy/NaWfGS8M9D2omQntntF0HrGgdeUi7j_YHABKgxocY5Nx8mfhXsQC3yxt-36dTyGmD3UwONW0JvcE5Pr6t2Bys6V61u6FF-ZrwK4","牙醫09",16,1));
        lstMemeMeme.add(new PublicMeme("1","1","#Happy Birthday","https://i.pinimg.com/originals/11/2c/79/112c79099635f40073d579cd237a9ad8.gif","江戶川先生",14,0));


    }
}
