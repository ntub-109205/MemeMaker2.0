package com.deep.photoeditor;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

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
        new AlertDialog.Builder(this)
                .setTitle("免責聲明")
                .setMessage("歡迎您使用「MemeMaker」(以下簡稱本應用)，任何瀏覽的人士，須自行承擔一切風險，本應用不會負責任何因瀏覽或使用本應用而引致之損失。本應用不會作出任何默示的擔保。\n" +
                        "本應用承諾力求網站內容之準確性及完整性，但內容如有錯誤或遺漏，本應用不會承擔任何賠償責任，所有本應用內容，將會隨時更改，而不作另行通知。\n"+
                        "本應用不會對使用或連結本應用而引致任何損害(包括但不限於系統固障、資料損失)、誹謗、侵犯版權或知識產權所造成的損失，包括但不限於利潤、商譽、使用、資料損失或其他無形損失，本應用不承擔任何直接、間接、附帶、特別、衍生性或懲罰性賠償。\n"+
                        "是否使用本應用之服務下載或取得任何資料應由用戶自行考慮且自負風險，因前開任何資料之下載而導致用戶設備系統之任何損壞或資料流失，本應用不承擔任何責任。")
                .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //你可以在這裡加入事件
                    }
                })
                .setCancelable(false)
                .show();
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
