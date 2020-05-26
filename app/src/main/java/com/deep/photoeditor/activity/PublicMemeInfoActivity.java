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
import com.deep.photoeditor.fragment.TempInfoFragment;
import com.deep.photoeditor.fragment.maintab2;
import com.wx.goodview.GoodView;

public class PublicMemeInfoActivity extends AppCompatActivity {
    private static final String TAG = "PublicMemeInfoActivity";
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;
    //goodview
    GoodView mGoodView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicmeme_info);
        Log.d(TAG, "onCreate: started.");

        //goodview
        mGoodView = new GoodView(this);

        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("公開梗圖");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager)findViewById(R.id.tempInfoViewPager);
        pagerAdapter = new PageAdapter(getSupportFragmentManager());

        //Add Fragment here
        pagerAdapter.AddFragment(new maintab2(),"相關梗圖");
        viewPager.setAdapter(pagerAdapter);


        getIncomingIntent();
    }
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("meme_url") && getIntent().hasExtra("hashTag") && getIntent().hasExtra("user_name") && getIntent().hasExtra("like_sum")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String memeUrl = getIntent().getStringExtra("meme_url");
            String hashTag = getIntent().getStringExtra("hashTag");
            String userName = getIntent().getStringExtra("user_name");
            int likeSum = getIntent().getIntExtra("like_sum", 0);

            setInfo(memeUrl, hashTag, userName, likeSum);
        }
    }
    private void setInfo(String memeUrl, String hashTag, String userName, int likeSum){
        Log.d(TAG, "setInfo: set memeUrl hashTag userName likeSum");

        //設置模板名
        TextView name = findViewById(R.id.hashTag);
        name.setText(hashTag);

        //設置模板圖片
        ImageView image = findViewById(R.id.memeImage);
        Glide.with(this)
                .asBitmap()
                .load(memeUrl)
                .into(image);
        //設置製作者名
        TextView user = findViewById(R.id.madeByUser);
        user.setText(userName);
        //設置熱門程度(被使用次數)
        TextView fireNum = findViewById(R.id.likeNum);
        fireNum.setText(String.valueOf(likeSum));


    }

    //onclick判斷在xml
    public void bookmark(View view) {
        ((ImageView) view).setImageResource(R.drawable.bookmark_checked);
        mGoodView. setTextInfo("收藏成功", Color.parseColor("#f66467"), 12);
        mGoodView.show(view);
    }
    public void like(View view) {
        ((ImageView) view).setImageResource(R.drawable.like_checked);
        mGoodView. setTextInfo("+1", Color.parseColor("#f66467"), 12);
        mGoodView.show(view);
    }
}
