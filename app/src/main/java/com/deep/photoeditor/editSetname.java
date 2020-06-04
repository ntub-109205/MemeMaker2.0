package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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

public class editSetname extends AppCompatActivity {
    public Button btnNext;
    public Button btnFinishtemplate;
    public EditText setName;
    private static variable variable = new variable();

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

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){     //製作梗圖
                String templateName = setName.getText().toString();
                variable.templateNameSetter(templateName);
                Intent edit = new Intent(editSetname.this,EditImageActivity.class);
                startActivity(edit);
            }
        });

        btnFinishtemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){    //完成模板
                String templateName = setName.getText().toString();
                variable.templateNameSetter(templateName);
                Intent edit = new Intent(editSetname.this,editOnlymaketemp.class);
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
