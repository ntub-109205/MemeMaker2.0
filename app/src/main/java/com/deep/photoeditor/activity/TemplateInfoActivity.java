package com.deep.photoeditor.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.deep.photoeditor.EditImageActivity;
import com.deep.photoeditor.adpater.PageAdapter;
import com.deep.photoeditor.R;
import com.deep.photoeditor.api;
import com.deep.photoeditor.fragment.TempInfoFragment;
import com.deep.photoeditor.variable;
import com.felipecsl.gifimageview.library.GifImageView;
import com.google.android.material.snackbar.Snackbar;
import com.wx.goodview.GoodView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class TemplateInfoActivity extends AppCompatActivity {
    private static final String TAG = "TemplateInfoActivity";
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;
    private static com.deep.photoeditor.variable variable = new variable();
    private ProgressDialog mProgressDialog;
    public Button btnDomeme;
    //api
    private static api callApi = new api();

    private Dialog mDialog;
    //goodview
    private GoodView mGoodView;
    //給相關梗圖用的tempid
    private static String tempId;
    private static int saved; //是否被收藏

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_info);
        getIncomingIntent();
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

        //init Dialog
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.dialog_gif);


        btnDomeme = (Button)findViewById(R.id.domeme);
        btnDomeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                variable.useMyImageSetter(Boolean.FALSE);
                Intent edit = new Intent(TemplateInfoActivity.this, EditImageActivity.class);
                startActivity(edit);
            }
        });

        String temp="";
        try {
            temp = callApi.get("http://140.131.115.99/api/template/saved/"+tempId).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //{"saved":"0"}
        saved =Integer.parseInt(temp.substring(10,11));
        //設置收藏顏色
        ImageView bookMark = findViewById(R.id.bookmark);
        if (saved==0) {
            bookMark.setImageResource(R.drawable.bookmark);
        }else {
            bookMark.setImageResource(R.drawable.bookmark_checked);
        }
    }
    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("temp_id") &&getIntent().hasExtra("temp_url") && getIntent().hasExtra("temp_name") && getIntent().hasExtra("user_name") && getIntent().hasExtra("used_sum")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            //模板id給相關梗圖fragment用
            tempId = getIntent().getStringExtra("temp_id");
            variable.templateIDSetter(tempId);

            //下面這些放到cardView
            String tempUrl = getIntent().getStringExtra("temp_url");
            variable.templateImageSetter(getBitmap(tempUrl));

            String tempName = getIntent().getStringExtra("temp_name");
            String userName = getIntent().getStringExtra("user_name");
            int usedSum = getIntent().getIntExtra("used_sum", 0);

            setInfo(tempUrl, tempName, userName, usedSum);
            showImageDialog(tempUrl,tempName);

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

    private void showImageDialog(String tempUrl,String tempName) {
        ImageView image = findViewById(R.id.tempImage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GifImageView dialogImage = (GifImageView) mDialog.findViewById(R.id.gif_view);
                TextView dialogTag = (TextView) mDialog.findViewById(R.id.gif_tag);
                ImageView diaglogClose = (ImageView) mDialog.findViewById(R.id.dialog_close);
                //show GIF by using Glide
                Glide.with(view).load(tempUrl).into(dialogImage);
                dialogTag.setText(tempName);
                diaglogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
            }
        });
    }

    public static String returnTempIdString(){
        return tempId;
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
            callApi.post("http://140.131.115.99/api/template/saved","template_id=" + tempId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public void shareLine(View view) {
        if (getPermission()) {
            //確認權限
        }else {
            showLoading("儲存中...");
            saveImageToGallery(this,variable.templateImageGetter());
            String scheme ="line://msg/image"+variable.memePathGetter();
            this.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(scheme)));
        }
    }

    public void saveImage(View view) {
        if (getPermission()) {
            //確認權限
        }else {
            showLoading("儲存中...");
            saveImageToGallery(this,variable.templateImageGetter());
        }
    }

    public boolean getPermission() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            return true;
        }
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[]{},1);
//        }
        return false;
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先儲存圖片
        String path = "MeMe Maker";
        File appDir = new File(Environment.getExternalStorageDirectory(), path);
        Log.d(TAG, "appDir: "+appDir);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        variable.memePathSetter(file.toString());

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            hideLoading();
            showSnackbar("儲存成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showSnackbar("儲存失敗");
            hideLoading();
        } catch (IOException e) {
            e.printStackTrace();
            showSnackbar("儲存失敗");
            hideLoading();
        }

        // 其次把檔案插入到系統圖庫
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最後通知相簿更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }

    protected void showLoading(@NonNull String message) {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(message);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    protected void hideLoading() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    protected void showSnackbar(@NonNull String message) {
        View view = findViewById(android.R.id.content);
        if (view != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
