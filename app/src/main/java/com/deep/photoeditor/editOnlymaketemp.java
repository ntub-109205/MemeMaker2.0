package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class editOnlymaketemp extends AppCompatActivity {
    //button onClick to next page
    Switch switchTemp;
    public Button btnNext;
    Uri uri;

    public void init(){
        btnNext = (Button)findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //new一個intent物件，並指定Activity切換的class
                Intent edit = new Intent(editOnlymaketemp.this,editShare.class);
                edit.setData(uri);
                //切換Activity
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_onlymaketemp);

        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //switch
        switchTemp = findViewById(R.id.tempSwitch);

        //將switch的狀態儲存到shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        //---switchTemp---
        switchTemp.setChecked(sharedPreferences.getBoolean("value",true));

        switchTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchTemp.isChecked()) {
                    //當switch被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switchTemp.setChecked(true);
                }else{
                    //當switch沒被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switchTemp.setChecked(false);
                }
            }
        });

        init();
    }
}
