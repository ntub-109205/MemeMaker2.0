package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.deep.photoeditor.base.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;


public class editOnlymaketemp extends BaseActivity {
    Switch switchTemp;
    public Button btnNext;
    public ImageView imageView;
    public TextView textView;
    private static Context mContext;
    private static api callApi = new api();
    private static variable variable = new variable();

    public String templateName;
    public String templateShare = "1";
    Uri templateUri;

    public void init(){
        btnNext = (Button)findViewById(R.id.btnNext);
        imageView = (ImageView)findViewById(R.id.imageView);
        textView = (TextView)findViewById(R.id.textView);
        textView.setText(templateName);
        switchTemp = findViewById(R.id.tempSwitch);
        //將switch的狀態儲存到shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        //---switchTemp---
        switchTemp.setChecked(sharedPreferences.getBoolean("value",true));
        switchTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchTemp.isChecked()) { //公開
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switchTemp.setChecked(true);
                    templateShare = "1";
                }else{    //不公開
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switchTemp.setChecked(false);
                    templateShare = "0";
                }
                variable.templateShareSetter(templateShare);
            }
        });
        ContentResolver cr = this.getContentResolver();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(templateUri));
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(),e);
        }
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                showLoading("載入中...");
                Log.d("contextQQ","path="+getFilePathForN(mContext,templateUri));
                try {
                    callApi.post("http://140.131.115.99/api/txt/templateStore",
                            "category_id="+variable.category_idGetter()+"&name=" + templateName +"&share=" +templateShare );
                    Log.d("contextQQ","傳字串=" + callApi.returnString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Log.d("contextQQ",callApi.multipartRequest("http://140.131.115.99/api/template/store","str="+
                            URLEncoder.encode(templateName, "UTF-8"),getFilePathForN(mContext,templateUri),"image" ));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent edit = new Intent(editOnlymaketemp.this,editShare.class);
                startActivity(edit);
                hideLoading();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        setContentView(R.layout.activity_edit_onlymaketemp);
        mContext = getApplicationContext();
        templateUri = variable.templateUriGetter();
        templateName = variable.templateNameGetter();
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private static String getFilePathForN(Context context, Uri uri) {
        try {
            Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String name = (returnCursor.getString(nameIndex));
            File file = new File(context.getFilesDir(), name);
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            returnCursor.close();
            inputStream.close();
            outputStream.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
