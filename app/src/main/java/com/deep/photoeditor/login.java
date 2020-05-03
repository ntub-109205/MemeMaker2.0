package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

public class login extends AppCompatActivity {
    //button onClick to next page
    public Button btnSignin;
    public Button btnToSignup;

    public void init(){
        btnSignin = (Button)findViewById(R.id.btnSignin);
        btnToSignup = (Button)findViewById(R.id.btnToSignup);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //new一個intent物件，並指定Activity切換的class
                Intent edit = new Intent(login.this,MainActivity.class);
                //切換Activity
                startActivity(edit);
            }
        });

        btnToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //new一個intent物件，並指定Activity切換的class
                Intent edit = new Intent(login.this,signUp.class);
                //切換Activity
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
