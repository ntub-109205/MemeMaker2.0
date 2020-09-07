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
import com.deep.photoeditor.adpater.PageAdapter;
import com.deep.photoeditor.R;
import com.deep.photoeditor.fragment.TempInfoFragment;
import com.wx.goodview.GoodView;

import java.security.cert.PKIXCertPathBuilderResult;

public class TemplateInfoActivity extends AppCompatActivity {
    private static final String TAG = "TemplateInfoActivity";
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;
    //goodview
    GoodView mGoodView;
    //給相關梗圖用的tempid
    private static String tempId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_info);
        Log.d(TAG, "onCreate: started.");

        //goodview
        mGoodView = new GoodView(this);

        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("公開模板");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager)findViewById(R.id.tempInfoViewPager);
        pagerAdapter = new PageAdapter(getSupportFragmentManager());

        //Add Fragment here
        pagerAdapter.AddFragment(new TempInfoFragment(),"相關梗圖");
        viewPager.setAdapter(pagerAdapter);


        getIncomingIntent();
    }
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("temp_id") &&getIntent().hasExtra("temp_url") && getIntent().hasExtra("temp_name") && getIntent().hasExtra("user_name") && getIntent().hasExtra("used_sum")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            //模板id給相關梗圖fragment用
            tempId = getIntent().getStringExtra("temp_id");
            //下面這些放到cardView
            String tempUrl = getIntent().getStringExtra("temp_url");
            String tempName = getIntent().getStringExtra("temp_name");
            String userName = getIntent().getStringExtra("user_name");
            int usedSum = getIntent().getIntExtra("used_sum", 0);

            setInfo(tempUrl, tempName, userName, usedSum);
            Log.d("temp", "tempid="+returnTempIdString());
        }
    }
    private void setInfo(String tempUrl, String tempName, String userName, int usedSum){
        Log.d(TAG, "setInfo: set tempUrl tempName userName usedSum");

        //設置模板名
        TextView name = findViewById(R.id.tempName);
        name.setText(tempName);

        //設置模板圖片
        ImageView image = findViewById(R.id.tempImage);
        Glide.with(this)
                .asBitmap()
                .load(tempUrl)
                .into(image);
        //設置製作者名
        TextView user = findViewById(R.id.madeByUser);
        user.setText(userName);
        //設置熱門程度(被使用次數)
        TextView fireNum = findViewById(R.id.fireNum);
        fireNum.setText(String.valueOf(usedSum));
    }

    public static String returnTempIdString(){
        return tempId;
    }

    //onclick判斷在xml
    public void bookmark(View view) {
        ((ImageView) view).setImageResource(R.drawable.bookmark_checked);
        mGoodView. setTextInfo("收藏成功", Color.parseColor("#f66467"), 12);
        mGoodView.show(view);
    }
}
