package com.deep.photoeditor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.deep.photoeditor.adpater.RecyclerViewAdapter__gif;
import com.deep.photoeditor.gifmake.GifMakeActivity;
import com.deep.photoeditor.gifmake.VideoToGifActivity;

import java.util.ArrayList;
import java.util.List;


public class GifMain extends AppCompatActivity {
    private static final String TAG = "GifMain";

    private RecyclerView recyclerView;
    private List<PublicMeme> lstMineGif;
    RecyclerViewAdapter__gif recyclerViewAdapter_myGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_main);
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lstMineGif = new ArrayList<>();
        lstMineGif.add(new PublicMeme("1","#彩虹貓","https://www.urad.com.tw/wp-content/uploads/2015/08/giphy.gif","jessie",30));
        lstMineGif.add(new PublicMeme("1","#地球","https://upload.wikimedia.org/wikipedia/commons/2/2c/Rotating_earth_%28large%29.gif","Geo",20));
        lstMineGif.add(new PublicMeme("1","#早安","https://i.pinimg.com/originals/87/40/f7/8740f719676a6393b9acb80e1f99b97f.gif","跑跑跑的人",19));
        lstMineGif.add(new PublicMeme("1","#孫儷","https://i1.kknews.cc/SIG=ko35sv/39o90003725ro568no98.jpg","好累",18));
        lstMineGif.add(new PublicMeme("1","#0元特效","https://i3.read01.com/SIG=64obee/304c6a336773744e6265.jpg","Anc1233",17));
        lstMineGif.add(new PublicMeme("1","#迪麗熱巴","https://i2.kknews.cc/SIG=2pfm603/39oq0001ps2s2ro7ns97.jpg","牙醫09",16));
        lstMineGif.add(new PublicMeme("1","#剛穿新鞋的你","https://i3.read01.com/SIG=1crbr31/304c6a33677341366b4d.jpg","江戶川先生",14));

        recyclerView = findViewById(R.id.myGifRecyclerview);
        recyclerViewAdapter_myGif = new RecyclerViewAdapter__gif(this, lstMineGif);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter_myGif);
    }


    //由圖片做Gif
    public void createGif(View view) {
        startActivity(new Intent(this, GifMakeActivity.class));
    }
    //由影片做Gif
    public void createVideoGif(View view) {
        startActivity(new Intent(this, VideoToGifActivity.class));
    }
}
