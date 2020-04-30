package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class editShare extends AppCompatActivity {
    //button onClick to next page
    public Button btnNext;

    public void init(){
        btnNext = (Button)findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //new一個intent物件，並指定Activity切換的class
                Intent edit = new Intent(editShare.this,MainActivity.class);
                //切換Activity
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_share);
        init();
    }
}
