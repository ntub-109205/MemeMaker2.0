package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;

public class editPublicsetting extends AppCompatActivity {
    Switch switchMeme;
    public Button btnNext;
    public TextView txtSetTag;
    private static variable variable = new variable();
    private static api callApi = new api();
    public String memeShare = "1";
    public String tag = "";
    Uri memeUri;

    public void init(){
        btnNext = (Button)findViewById(R.id.btnNext);
        txtSetTag = (TextView)findViewById(R.id.setName);
        ContentResolver cr = this.getContentResolver();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(memeUri));
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(),e);
        }
        //switch
        switchMeme = findViewById(R.id.memeSwitch);
        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);

        //---switchMeme---
        switchMeme.setChecked(sharedPreferences.getBoolean("value",true));
        if (switchMeme.isChecked()) {memeShare = "1";}
        else{memeShare = "0";}
        variable.memeShareSetter(memeShare);
        switchMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchMeme.isChecked()) {
                    //當switch被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switchMeme.setChecked(true);
                    memeShare = "1";
                }else{
                    //當switch沒被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switchMeme.setChecked(false);
                    memeShare = "0";
                }
                variable.memeShareSetter(memeShare);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                tag = txtSetTag.getText().toString();
                int len = tag.length();
                int x = 0;
                ArrayList a=new ArrayList();
                Log.d("tag1", "New完了");

                a.add(tag.indexOf("#"));//*第一個出現的索引位置
                Log.d("tag1", Integer.toString((Integer)a.get(x)));

                while ((Integer)a.get(x)!= -1) {
                    Log.d("tag1", "進入迴圈");
                    x+=1;

                    a.add(tag.indexOf("#", (Integer)a.get(x-1)+1));//*從這個索引往後開始第一個出現的位置
                    Log.d("tag1", Integer.toString((Integer)a.get(x)));

                }
                a.remove(a.size()-1);
                Log.d("tag1", "出迴圈");
                Log.d("tag1", String.valueOf(a.size()));
                for(int i=0;i<a.size();i++){
                    Log.d("tag1","List的值：" + a.get(i).toString());
                }
                ArrayList list=new ArrayList();
                for(int i=0;i<a.size()-1;i++) {
                    list.add(tag.substring((Integer)a.get(i)+1,(Integer)a.get(i+1)));
                }
                list.add(tag.substring((Integer)a.get(a.size()-1)+1,len));
                for(int i=0;i<a.size();i++) {
                    Log.d("tag1", "List的值：" + list.get(i).toString());
                }
                tag = "[";
                for(int i=0;i<a.size();i++) {
                    tag+="\""+i+"\" => \"" + list.get(i).toString()+"\"";
                    if (i<a.size()-1){tag+=", ";}
                }
                tag += "]";
                Log.d("tag1", "tag的值：" + tag);
                Log.d("contextQQ","Share=" + variable.memeShareGetter());
                try {
                    callApi.post("http://140.131.115.99/api/txt/memeStore",
                            "template_id="+variable.templateIDGetter()+
                                    "&meme_share="+variable.memeShareGetter()+
                            "&tags="+tag);
                    Log.d("contextQQ","傳字串=" + callApi.returnString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    Log.d("contextQQ",callApi.multipartRequest("http://140.131.115.99/api/meme/store","str="
                           ,variable.memePathGetter(),"meme_image" ));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent edit = new Intent(editPublicsetting.this,editShare.class);
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_publicsetting);
        memeUri = variable.memeUriGetter();
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NachoTextView nachoTextView = findViewById(R.id.memeTag);
        nachoTextView.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);

        init();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

}
