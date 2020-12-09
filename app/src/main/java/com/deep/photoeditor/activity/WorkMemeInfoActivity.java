package com.deep.photoeditor.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.deep.photoeditor.R;
import com.deep.photoeditor.adpater.PageAdapter;
import com.deep.photoeditor.api;
import com.wx.goodview.GoodView;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class WorkMemeInfoActivity extends AppCompatActivity {
    private static final String TAG = "WorkMemeInfoActivity";
    private static api callApi = new api();
    public PageAdapter pagerAdapter;
    private static String memeId;
    int meme_id;
    int meme_share;
    String table = "meme";
    String field = "share";
    Switch switchTemp;
    int share_num;
    GoodView mGoodView;
    private static int saved; //是否被收藏
    public void init(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_meme_info);
        //取得資訊
        getIncomingIntent();
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //goodview
        mGoodView = new GoodView(this);

        //---switchTemp預設---
        switchTemp = findViewById(R.id.tempSwitch);
        if (meme_share==0){
            switchTemp.setChecked(false);
        }else{
            switchTemp.setChecked(true);
        }
        switchTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchTemp.isChecked()) {
                    //當switch被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switchTemp.setChecked(true);
                    share_num=1;
                    Log.d("memeinfoactivity1","公開");

                }else{
                    //當switch沒被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    share_num=0;
                    Log.d("memeinfoactivity1","不公開");
                    switchTemp.setChecked(false);
                }
                try {
                    callApi.post("http://140.131.115.99/api/profile/update",
                            "table= " + table +"&id=" + meme_id +"&field= " + field + "&value= " + share_num );
                    Log.d("contextQQ","傳字串=" + callApi.returnString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        String temp="";
        Log.d("printMemeId up",""+memeId);
        try {
            temp = callApi.get("http://140.131.115.99/api/meme/saved/"+memeId).trim();
            Log.d("savedMemetemp",""+temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("savedMeme",(callApi.get("http://140.131.115.99/api/meme/saved/"+memeId)));
        saved =Integer.parseInt(temp.substring(10,11));
        Log.d("saved====", ""+saved);
        //設置收藏顏色
        ImageView bookMark = findViewById(R.id.bookmark);
        if (saved==0) {
            bookMark.setImageResource(R.drawable.bookmark);
        }else {
            bookMark.setImageResource(R.drawable.bookmark_checked);
        }

        init();

    }
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("meme_url") && getIntent().hasExtra("hashTag") && getIntent().hasExtra("user_name") && getIntent().hasExtra("like_sum")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String memeUrl = getIntent().getStringExtra("meme_url");
            String hashTag = getIntent().getStringExtra("hashTag");
            String userName = getIntent().getStringExtra("user_name");
            memeId = getIntent().getStringExtra("meme_id");
            Log.d("printMemeId down",memeId);
            meme_id = Integer.parseInt(memeId);
            int likeSum = getIntent().getIntExtra("like_sum", 0);
            int shared = getIntent().getIntExtra("meme_share", 0);
            Log.d("memeinfoactivity1","shared :"+ shared+"memeid: "+memeId);
            setInfo(memeUrl, hashTag, userName, likeSum,shared,memeId);
        }
    }
    private void setInfo(String memeUrl, String hashTag, String userName, int likeSum, int shared, String memeId){
        Log.d(TAG, "setInfo: set memeUrl hashTag userName likeSum");

        //設置模板名
        TextView name = findViewById(R.id.workMemHashtag);
        name.setText(hashTag);

        //設置模板圖片
        ImageView image = findViewById(R.id.imageView);
        Glide.with(this)
                .load(memeUrl)
                .into(image);

        //設置熱門程度(被使用次數)
        TextView fireNum = findViewById(R.id.fireNum);
        fireNum.setText(String.valueOf(likeSum));
        Log.d("memeinfoactivity1", "memeinfoactivity1 " + shared);
        meme_share = shared;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    //onclick判斷在xml
    public void bookmark(View view) {
        if (saved == 0) {
            ((ImageView) view).setImageResource(R.drawable.bookmark_checked);
            mGoodView. setTextInfo("收藏成功", Color.parseColor("#f66467"), 12);
            mGoodView.show(view);
            saved=1;
        } else {
            ((ImageView) view).setImageResource(R.drawable.bookmark);
            mGoodView. setTextInfo("取消收藏", Color.parseColor("#999da4"), 12);
            mGoodView.show(view);
            saved=0;
        }
        try {
            callApi.post("http://140.131.115.99/api/meme/saved","meme_id=" + memeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void like(View view) {
        ((ImageView) view).setImageResource(R.drawable.like_checked);
        mGoodView. setTextInfo("+1", Color.parseColor("#f66467"), 12);
        mGoodView.show(view);
    }
}
