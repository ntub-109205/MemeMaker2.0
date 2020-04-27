package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.FileNotFoundException;



public class editTools extends AppCompatActivity {
    //button onClick to next page
    public Button btnNext;
    public TextView txt1;
    public String name;
    Uri uri;
    public void init(){
        btnNext = (Button)findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent edit = new Intent(editTools.this,editPublicsetting.class);
                edit.setData(uri);
                edit.putExtra("name",name);
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tools);

        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        uri = getIntent().getData();

        Intent intent = this.getIntent();       //取得傳遞過來的資料
        name = intent.getStringExtra("name");
        txt1 = (TextView)findViewById(R.id.textView4);
        txt1.setText(name);
        Log.e("uri", uri.toString());
        //抽象資料的接口
        ContentResolver cr = this.getContentResolver();
        try {
            //由抽象資料接口轉換圖檔路徑為Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            //取得圖片控制項ImageView
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            // 將Bitmap設定到ImageView
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(),e);
        }
        init();
    }
}
