package com.deep.photoeditor;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //以下撰寫跳轉畫面的程式碼
        //啟動執行序
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    startActivity(new Intent().setClass(WelcomeActivity.this,MainActivity.class));
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
