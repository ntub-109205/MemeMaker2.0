package com.deep.photoeditor;

import androidx.annotation.NonNull;
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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.hootsuite.nachos.ChipConfiguration;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.ChipSpan;
import com.hootsuite.nachos.chip.ChipSpanChipCreator;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.hootsuite.nachos.tokenizer.SpanChipTokenizer;

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
        txtSetTag = (TextView)findViewById(R.id.memeTag);
        // txtSetTag.setText("#");
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
        if (variable.category_idGetter().equals("1")){switchMeme.setText("梗圖公開");}
        else if(variable.category_idGetter().equals("2")){switchMeme.setText("長輩圖公開");}

        
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
                tag = txtSetTag.getText().toString().trim();
                Log.d("tag1", tag);
                int len = tag.length();
                int x = 0;
                ArrayList a=new ArrayList();
                a.add(tag.indexOf("#"));//*第一個出現的索引位置
                while ((Integer)a.get(x)!= -1) {
                    x+=1;
                    a.add(tag.indexOf("#", (Integer)a.get(x-1)+1));//*從這個索引往後開始第一個出現的位置
                }
                a.remove(a.size()-1);
                ArrayList list=new ArrayList();
                for(int i=0;i<a.size()-1;i++) {
                    list.add(tag.substring((Integer)a.get(i)+1,(Integer)a.get(i+1)).trim());

                }
                list.add(tag.substring((Integer)a.get(a.size()-1)+1,len).trim());
                tag = "";
                for(int i=0;i<a.size();i++) {
                    tag +="#" + list.get(i).toString().trim();
                }
                Log.d("tag1", tag);
                try {
                    callApi.post("http://140.131.115.99/api/txt/store",
                            "template_id="+variable.templateIDGetter()+
                                    "&share="+variable.memeShareGetter()+
                                    "&tags="+tag);
                    Log.d("contextQQ","傳字串=" + callApi.returnString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("contextTest","梗圖的filepath=" + variable.memePathGetter());

                try {
                    Log.d("contextQQ",callApi.multipartRequest("http://140.131.115.99/api/meme/store","str="
                           ,variable.memePathGetter(),"image" ));
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
        //Tag樣式
        createChipWithText();

        init();
    }

    private void createChipWithText() {
        NachoTextView nachoTextView = findViewById(R.id.memeTag);
        nachoTextView.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
        nachoTextView.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);

        nachoTextView.setChipTokenizer(new SpanChipTokenizer<>(this, new ChipSpanChipCreator() {
            @Override
            public ChipSpan createChip(@NonNull Context context, @NonNull CharSequence text, Object data) {
                return new ChipSpan(context, '#' + text.toString(),null, data);
            }

            @Override
            public void configureChip(@NonNull ChipSpan chip, @NonNull ChipConfiguration chipConfiguration) {
                super.configureChip(chip, chipConfiguration);
            }
        }, ChipSpan.class));
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

}
