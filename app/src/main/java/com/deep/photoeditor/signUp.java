package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class signUp extends AppCompatActivity {
    //button onClick to next page
    public Button btnSignup;
    public Button btnToLoginin;

    public void init(){
        btnSignup = (Button)findViewById(R.id.btnSignUp);
        btnToLoginin = (Button)findViewById(R.id.btnToLoginIn);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //new一個intent物件，並指定Activity切換的class
                Intent edit = new Intent(signUp.this,MainActivity.class);
                //切換Activity
                startActivity(edit);
            }
        });

        btnToLoginin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //new一個intent物件，並指定Activity切換的class
                Intent edit = new Intent(signUp.this,login.class);
                //切換Activity
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

    }
}
