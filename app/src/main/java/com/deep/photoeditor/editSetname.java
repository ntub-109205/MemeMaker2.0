package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.net.Uri;

import android.widget.TextView;
import java.io.FileNotFoundException;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
public class editSetname extends AppCompatActivity {
    //button onClick to next page
    public Button btnNext;
    public Button btnFinishtemplate;
    public EditText setName;
    Uri uri;
    public void init(){
        btnNext = (Button)findViewById(R.id.btnNext);
        btnFinishtemplate = (Button)findViewById(R.id.btnFinishtemplate);
        setName = (EditText)findViewById(R.id.setName);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //用getText取得輸入在EditText裡的內容
                String templateName = setName.getText().toString();

                //new一個intent物件，並指定Activity切換的class
                Intent edit = new Intent(editSetname.this,EditImageActivity.class);
                //切換Activity
                edit.setData(uri);
                edit.putExtra("name",templateName);
                startActivity(edit);
            }
        });

        btnFinishtemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //用getText取得輸入在EditText裡的內容
                String templateName = setName.getText().toString();

                //new一個intent物件，並指定Activity切換的class
                Intent edit = new Intent(editSetname.this,editOnlymaketemp.class);
                //切換Activity
                edit.setData(uri);
                edit.putExtra("name",templateName);
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_setname);
        uri = getIntent().getData();

        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.e("uri", uri.toString());
        //抽象資料的接口
        ContentResolver cr = this.getContentResolver();
        try {
            //由抽象資料接口轉換圖檔路徑為Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            //取得圖片控制項ImageView
            ImageView imageView = (ImageView) findViewById(R.id.imageView3);
            // 將Bitmap設定到ImageView
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(),e);
        }
        init();



    }
}
