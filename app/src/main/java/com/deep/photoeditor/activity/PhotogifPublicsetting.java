package com.deep.photoeditor.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.deep.photoeditor.R;
import com.deep.photoeditor.api;
import com.deep.photoeditor.bean.GifImageFrame;
import com.deep.photoeditor.editPublicsetting;
import com.deep.photoeditor.editShare;
import com.deep.photoeditor.gifmake.GifMakePresenter;
import com.deep.photoeditor.variable;
import com.felipecsl.gifimageview.library.GifImageView;
import com.hootsuite.nachos.ChipConfiguration;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.ChipSpan;
import com.hootsuite.nachos.chip.ChipSpanChipCreator;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.hootsuite.nachos.tokenizer.SpanChipTokenizer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class PhotogifPublicsetting extends AppCompatActivity {

    private static final String TAG = "PhotoToGif";
    private static com.deep.photoeditor.variable variable = new variable();
    Switch switchMeme;
    private static api callApi = new api();
    public String gifShare = "1";
    public String tag = "";

    public void init(){
        GifImageView gifView = findViewById(R.id.imageView);
        Button btnNext = findViewById(R.id.btnNext);
        TextView txtSetTag = findViewById(R.id.memeTag);

        //顯示gif
        byte[] fileBytes=variable.getGifByteArray();
        Log.d(TAG, "fileBytes："+ Arrays.toString(fileBytes));
        gifView.setBytes(fileBytes);
        gifView.startAnimation();

        ContentResolver cr = this.getContentResolver();

        //switch
        switchMeme = findViewById(R.id.memeSwitch);

        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);

        //---switchMeme---
        switchMeme.setChecked(sharedPreferences.getBoolean("value",true));
        if (switchMeme.isChecked()) {gifShare = "1";}
        else{gifShare = "0";}
        variable.memeShareSetter(gifShare);
        switchMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchMeme.isChecked()) {
                    //當switch被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switchMeme.setChecked(true);
                    gifShare = "1";
                }else{
                    //當switch沒被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switchMeme.setChecked(false);
                    gifShare = "0";
                }
                variable.memeShareSetter(gifShare);
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
                Log.d("tag1",variable.memeShareGetter());
                try {
                    callApi.post("http://140.131.115.99/api/txt/store",
                            "&share="+variable.memeShareGetter()+"&tags="+tag);
                    Log.d("gifff","傳字串=" + callApi.returnString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("tag1","gif的filepath=" + variable.getGifPath());

                try {
                    Log.d("gifff",callApi.multipartRequest("http://140.131.115.99/api/meme/store","str="
                            , variable.getGifPath(),"image" ));
//                    callApi.post("http://140.131.115.99/api/meme/store","&image="+variable.getGifPath());
//                    Log.d("gifff","傳=" + callApi.returnString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(PhotogifPublicsetting.this, GifShareActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_to_gif_main);
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
