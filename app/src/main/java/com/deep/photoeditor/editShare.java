package com.deep.photoeditor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.deep.photoeditor.activity.MainActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class editShare extends AppCompatActivity {
    private static final String TAG = "editShare";
    public Button btnNext;
    public ImageView mImageView;
    private ProgressDialog mProgressDialog;
    private static variable variable = new variable();

    public void init() throws FileNotFoundException {
        btnNext = (Button)findViewById(R.id.btnNext);
        mImageView = (ImageView)findViewById(R.id.imageView);
        ContentResolver cr = this.getContentResolver();
        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(variable.memeUriGetter()));
        mImageView.setImageBitmap(bitmap);
        variable.memeImageSetter(bitmap);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent edit = new Intent(editShare.this, MainActivity.class);
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_share);
        try {
            init();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void shareLine(View view) {
        if (getPermission()) {
            //確認權限
        }else {
            showLoading("儲存中...");
            saveImageToGallery(this,variable.memeImageGetter());
            String scheme ="line://msg/image"+variable.memePathGetter();
            this.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(scheme)));
        }
    }

    public void saveImage(View view) {
        if (getPermission()) {
            //確認權限
        }else {
            showLoading("儲存中...");
            saveImageToGallery(this,variable.memeImageGetter());
        }
    }
    public boolean getPermission() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            return true;
        }
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
        Log.d(TAG, "路徑在哪: "+file);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            hideLoading();
            showSnackbar("儲存成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showSnackbar("儲存失敗1");
            hideLoading();
        } catch (IOException e) {
            e.printStackTrace();
            showSnackbar("儲存失敗2");
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
