package com.deep.photoeditor;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import com.deep.photoeditor.activity.MainActivity;
import com.deep.photoeditor.base.BaseActivity;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;

public class login extends BaseActivity {
    //button onClick to next page
    public Button btnSignin;
    public Button btnToSignup;
    public TextInputLayout txtloginEmail;
    public TextInputLayout txtloginPassword;
    public TextView promptMessage;
    public String loginEmail;
    public String loginPassword;
    private static String str3;
    private static api callApi = new api();
    private static String feedback;

    public void init(){
        btnSignin = (Button)findViewById(R.id.btnSignin);
        btnToSignup = (Button)findViewById(R.id.btnToSignup);
        txtloginEmail = (TextInputLayout) findViewById(R.id.loginEmail);
        txtloginPassword = (TextInputLayout) findViewById(R.id.loginPassword);
        promptMessage = (TextView) findViewById(R.id.promptMessage);
        //登入
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new一個intent物件，並指定Activity切換的class
                loginEmail = txtloginEmail.getEditText().getText().toString();
                loginPassword = txtloginPassword.getEditText().getText().toString();

                try {callApi.post("http://140.131.115.99/api/login", "email=" + loginEmail + "&password=" + loginPassword);
                    feedback = callApi.returnString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (feedback == null) {
                    promptMessage.setText("登入失敗");

                } else {
                    Log.d("logintest", feedback);
                    int x = 0;
                    ArrayList a = new ArrayList();
                    a.add(feedback.indexOf("\""));//*第一個出現的索引位置
                    while ((Integer) a.get(x) != -1) {
                        x += 1;
                        a.add(feedback.indexOf("\"", (Integer) a.get(x - 1) + 1));//*從這個索引往後開始第一個出現的位置
                    }

                    a.remove(a.size() - 1);
                    String str1 = feedback.substring((Integer) a.get(0) + 1, (Integer) a.get(1));
                    str3 = feedback.substring((Integer) a.get(4) + 1, (Integer) a.get(5));
                    callApi.setHeader(str3);

                    if (str1.equals("success")) {
                        showLoading("載入中...");
                        Intent edit = new Intent(login.this, MainActivity.class);
                        edit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(edit);
                    } else {
                        //hideLoading();
                        Log.d("logintest", "登入失敗");
                    }
                }
            }
        });

        //註冊
        btnToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent edit = new Intent(login.this,signUp.class);
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        setContentView(R.layout.activity_login);
        init();
    }

    //關閉鍵盤
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
