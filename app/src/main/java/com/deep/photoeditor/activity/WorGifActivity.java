package com.deep.photoeditor.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.deep.photoeditor.R;
import com.deep.photoeditor.adpater.PageAdapter;
import com.deep.photoeditor.fragment.WorGifFragment;
import com.deep.photoeditor.fragment.WorMemeFragment;
import com.wx.goodview.GoodView;

public class WorGifActivity extends AppCompatActivity {
    private static final String TAG = "WorGifActivity";
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;
    public int cnt;
    //goodview
    GoodView mGoodView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wor_gif);
        Log.d(TAG, "onCreate: started.");
        cnt = 7;
        //goodview
        mGoodView = new GoodView(this);

        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("我的動圖");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager)findViewById(R.id.worGifViewPager);
        pagerAdapter = new PageAdapter(getSupportFragmentManager());

        //Add Fragment here
        pagerAdapter.AddFragment(new WorGifFragment(),"相關動圖");
        viewPager.setAdapter(pagerAdapter);


        getIncomingIntent();
    }
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("temp_url") && getIntent().hasExtra("temp_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String tempUrl = getIntent().getStringExtra("temp_url");
            String tempName = getIntent().getStringExtra("temp_name");

            setImage(tempUrl, tempName);
        }
    }
    private void setImage(String tempUrl, String tempName){
        Log.d(TAG, "setImage: setting te image and name to widgets.");

        TextView name = findViewById(R.id.tempName);
        name.setText(tempName);

        ImageView image = findViewById(R.id.tempImage);
        Glide.with(this)
                .asBitmap()
                .load(tempUrl)
                .into(image);
    }

    //onclick判斷在xml
    public void bookmark(View view) {
        ((ImageView) view).setImageResource(R.drawable.bookmark_checked);
        mGoodView. setTextInfo("收藏成功", Color.parseColor("#f66467"), 12);
        mGoodView.show(view);
    }
}
