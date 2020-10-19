package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.deep.photoeditor.activity.MainActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class signUp extends AppCompatActivity {
    //button onClick to next page
    public Button btnSignup;
    public Button btnToLoginin;
    public TextInputLayout textInputUsername,textInputEmail,textInputPassword,textInputCheckpass;
    public String email,name,password,c_password;
    private static api callApi = new api();
    private static variable variable = new variable();

    public void init(){
        btnSignup = (Button)findViewById(R.id.btnSignUp);
        btnToLoginin = (Button)findViewById(R.id.btnToLoginIn);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                confirmInput();
                try {
                    callApi.post("http://140.131.115.99/api/register",
                            "email=" + email + "&name=" + name +"&password=" +password+"&c_password=" +c_password );
                    Log.d("postRegister", callApi.returnString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                callApi.setHeader(callApi.cutString().get(2).toString());
                //new一個intent物件，並指定Activity切換的class
                Intent edit = new Intent(signUp.this, MainActivity.class);
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

        textInputUsername = findViewById(R.id.username);
        textInputEmail = findViewById(R.id.email);
        textInputPassword = findViewById(R.id.password);
        textInputCheckpass = findViewById(R.id.checkPassword);

        init();

    }

    //驗證username
    private boolean validUsername(){
        name = textInputUsername.getEditText().getText().toString().trim();

        if (name.isEmpty()) {
            textInputUsername.setError("尚未填寫");
            return false;
        }else {
            textInputUsername.setError(null);
            return true;
        }
    }

    //驗證email
    private boolean validEmail(){
        email = textInputEmail.getEditText().getText().toString().trim();

        if (email.isEmpty()) {
            textInputEmail.setError("尚未填寫");
            return false;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textInputEmail.setError("不符合電子郵件的格式");
            return false;
        }else {
            textInputEmail.setError(null);
            return true;
        }
    }

    //驗證password
    private boolean validPassword(){
        password = textInputPassword.getEditText().getText().toString().trim();

        if (password.isEmpty()) {
            textInputPassword.setError("尚未填寫");
            return false;
        }else if(password.length()<6){
            textInputPassword.setError("不到六位");
            return false;
        }else {
            textInputPassword.setError(null);
            return true;
        }
    }

    private boolean checkPassword(){
        c_password = textInputCheckpass.getEditText().getText().toString().trim();

        if (c_password.isEmpty()) {
            textInputCheckpass.setError("尚未填寫");
            return false;
        }else if(!password.equals(c_password)){
            Log.d("postRegister", "|"+password+"|"+c_password +"|");
            textInputCheckpass.setError("密碼不相同!");
            return false;
        }else{
            textInputCheckpass.setError(null);
            return true;
        }
    }

    public void confirmInput(){
        if (!validUsername() | !validEmail() | !validPassword() | !checkPassword()) {
            return;
        }
        Toast.makeText(this,"正確",Toast.LENGTH_SHORT).show();
    }

    //關閉鍵盤
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
