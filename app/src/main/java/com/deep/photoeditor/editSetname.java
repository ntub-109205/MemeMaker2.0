package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;
import java.io.FileNotFoundException;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Switch;

public class editSetname extends AppCompatActivity {
    Switch switchTemp;
    public Button btnNext;
    public Button btnFinishtemplate;
    public EditText setName;
    private static variable variable = new variable();
    public String templateShare = "1";

    Uri templateUri;
    public void init(){
        btnNext = (Button)findViewById(R.id.btnNext);
        btnFinishtemplate = (Button)findViewById(R.id.btnFinishtemplate);
        setName = (EditText)findViewById(R.id.setName);

        ContentResolver cr = this.getContentResolver();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(templateUri));
            ImageView imageView = (ImageView) findViewById(R.id.imageView3);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(),e);
        }

        //Switch
        switchTemp = findViewById(R.id.tempSwitch);
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
                    templateShare = "1";
                }else{
                    //當switch沒被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switchTemp.setChecked(false);
                    templateShare = "0";
                }
                variable.templateShareSetter(templateShare);
            }
        });

        //製作梗圖
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String templateName = setName.getText().toString();
                variable.templateNameSetter(templateName);
                Intent edit = new Intent(editSetname.this,EditImageActivity.class);
                startActivity(edit);
            }
        });

        //完成模板
        btnFinishtemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String templateName = setName.getText().toString();
                variable.templateNameSetter(templateName);
                Intent edit = new Intent(editSetname.this,editShare.class);
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_setname);
        templateUri = variable.templateUriGetter();
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
