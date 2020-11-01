package com.deep.photoeditor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.deep.photoeditor.adpater.RecyclerViewAdapter__gif;
import com.deep.photoeditor.gifmake.GifMakeActivity;
import com.deep.photoeditor.gifmake.VideoToGifActivity;
import com.felipecsl.gifimageview.library.GifImageView;

import java.util.ArrayList;
import java.util.List;


public class GifMain extends AppCompatActivity {
    private static final String TAG = "GifMain";

    private RecyclerView recyclerView;
    private List<PublicMeme> lstMineGif;
    RecyclerViewAdapter__gif recyclerViewAdapter_myGif;
    private View.OnClickListener mMyGifListener;
    private View.OnClickListener mPublicGifListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif_main);
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initListener();

        lstMineGif = new ArrayList<>();
        lstMineGif.add(new PublicMeme("1","1","#我真可愛","https://lh3.googleusercontent.com/proxy/_aOVP7bqrBMjyM8izkVPTgzgmueVtmxlD71EBLbFbHI_E6jISa_hl9rsUri0KemCClp3GjuUWeZulFDVyfz50B_s9PerMAX9TXBCpSm_WG0uQ8BSHCmdFd2C","jessie",30,1));
        lstMineGif.add(new PublicMeme("1","1","#誰怕誰","https://4.bp.blogspot.com/-cvLAyXhtB3c/XQrc0yQCQ2I/AAAAAAAMlPE/wNN_5eU1Xe0SYainh1NefU-sBIYj8ZksACLcBGAs/s1600/AS0005411_03.gif","Geo",20,1));
        lstMineGif.add(new PublicMeme("1","1","#太好了","https://media1.tenor.com/images/200bec2e1191d7e08ee5e7832fd0a1bf/tenor.gif?itemid=11841779","跑跑跑的人",19,0));
        lstMineGif.add(new PublicMeme("1","1","#骷顱頭 #CUTE","https://helpx.adobe.com/content/dam/help/en/photoshop/how-to/create-animated-gif/jcr_content/main-pars/image_4389415/create-animated-gif_3a-v2.gif","好累",18,0));
        lstMineGif.add(new PublicMeme("1","1","#鬼滅之刃","https://imgur.com/CV5zi8A.gif","Anc1233",17,0));
        lstMineGif.add(new PublicMeme("1","1","#迪麗熱巴","https://lh3.googleusercontent.com/proxy/NaWfGS8M9D2omQntntF0HrGgdeUi7j_YHABKgxocY5Nx8mfhXsQC3yxt-36dTyGmD3UwONW0JvcE5Pr6t2Bys6V61u6FF-ZrwK4","牙醫09",16,1));
        lstMineGif.add(new PublicMeme("1","1","#Happy Birthday","https://i.pinimg.com/originals/11/2c/79/112c79099635f40073d579cd237a9ad8.gif","江戶川先生",14,0));


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
    public void createVideoGif(View view) { startActivity(new Intent(this,VideoToGifActivity.class)); }

    private void initListener() {
        mMyGifListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: clicked on: " + mData.get(position));
            }
        };

        mPublicGifListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG, "onClick: clicked on: " + mData.get(position));
            }
        };
    }


}
