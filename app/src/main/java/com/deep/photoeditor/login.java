package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class login extends AppCompatActivity {
    //button onClick to next page
    public Button btnSignin;
    public Button btnToSignup;
    public TextView txtloginEmail;
    public TextView txtloginPassword;
    public String loginEmail;
    public String loginPassword;
    private static BufferedReader reader;
    private static StringBuffer response;

    public void init(){
        btnSignin = (Button)findViewById(R.id.btnSignin);
        btnToSignup = (Button)findViewById(R.id.btnToSignup);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //new一個intent物件，並指定Activity切換的class
                txtloginEmail = (TextView) findViewById(R.id.loginEmail);
                txtloginPassword = (TextView) findViewById(R.id.loginPassword);
                //txtloginEmail.setText("kevin0507a@gmail.com");
                //txtloginPassword.setText("wedxzas12345");

                loginEmail = txtloginEmail.getText().toString();
                loginPassword = txtloginPassword.getText().toString();
//                loginEmail = "kevin0507a@gmail.com";
//                loginPassword = "wedxzas12345";
                Log.d("Login","1");

                Map<String, String> map = new HashMap<>();
                //加入元素，加入時如果出現相同的鍵，則新的値會覆蓋原有的鍵對應值，並put方法會返回被覆蓋的値
                map.put("email", loginEmail);
                map.put("password", loginPassword);
                try {//QQQ
                    doWebPost("http://140.131.115.99/login","email=" + loginEmail +"&password=" + loginPassword);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("Login", returnResult());
                int x = 0;
                ArrayList a=new ArrayList();
                Log.d("Login", "New完了");

                a.add(returnResult().indexOf("\""));//*第一個出現的索引位置
                Log.d("Login", Integer.toString((Integer)a.get(x)));

                while ((Integer)a.get(x)!= -1) {
                    Log.d("Login", "進入迴圈");
                    x+=1;

                    a.add(returnResult().indexOf("\"", (Integer)a.get(x-1)+1));//*從這個索引往後開始第一個出現的位置
                    Log.d("Login", Integer.toString((Integer)a.get(x)));

                }
                Log.d("Login", "出迴圈");
                String str1 = returnResult().substring((Integer)a.get(0)+1,(Integer)a.get(1));
                String str2 = returnResult().substring((Integer)a.get(2)+1,(Integer)a.get(3));


                Log.d("Login", str1);
                Log.d("Login", str2);
                Log.d("Login", "切割完成");
                if (str1.equals("success")){
                    Intent edit = new Intent(login.this, MainActivity.class);
                    startActivity(edit);
                }else{
                    Log.d("Login", "登入失敗");

                }
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
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        setContentView(R.layout.activity_login);
        init();
    }
    public static void doWebPost(String httpMethodStr, String parameterData) throws Exception{
        try {
            URL url = new URL(httpMethodStr);//创建连接
            URLConnection urlConnection = url.openConnection();//声明一个抽象类URLConnection的引用urlConnection
            // 此处的urlConnection对象实际上是根据URL的请求协议(此处是http)生成的URLConnection类的子类HttpURLConnection,故此处最好将其转化为
            // HttpURLConnection类型的对象,以便用到HttpURLConnection更多的API.如下:
            HttpURLConnection connection = (HttpURLConnection) urlConnection; //声明一个抽象类HttpURLConnection的引用connection
            //URLConnection和HttpURLConnection使用的都是java.net中的类，属于标准的java接口。
            //HttpURLConnection继承自URLConnection,差别在与HttpURLConnection仅仅针对Http连接。
            connection.setRequestMethod("POST");//设定请求方式为"POST"，默认为"GET"
            connection.setDoOutput(true);//设置是否向HttpUrlConnction输出，因为这个是POST请求，参数要放在http正文内，因此需要设为true，默认情况下是false
            connection.setDoInput(true);//设置是否向HttpUrlConnection读入，默认情况下是true
            connection.setUseCaches(false);//POST请求不能使用缓存（POST不能被缓存）
            connection.setConnectTimeout(20*1000);//设置连接主机超时（单位：毫秒）
            connection.setReadTimeout(20*1000);//设置从主机读取数据超时（单位：毫秒）
            connection.connect();//connect()函数会根据HttpURLConnection对象的配置值 生成http头部信息，因此在调用connect函数之前，就必须把所有的配置准备好
            //HttpURLConnection是基于HTTP协议的，其底层通过socket通信实现。如果不设置超时（timeout），在网络异常的情况下，可能会导致程序僵死而不继续往下执行。
            //正文的内容是通过outputStream流写入的，实际上outputStream不是一个网络流，充其量是个字符串流，往里面写入的东西不会立即发送到网络，
            //而是存在于内存缓冲区中，待outputStream流关闭时，根据输入的内容生成http正文。至此，http请求的东西已经全部准备就绪
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            byte[] t = parameterData.getBytes("utf-8");
            dataOutputStream.write(t);
            dataOutputStream.flush();
            dataOutputStream.close();
            //对outputStream的写操作，又必须要在inputStream的读操作之前
            InputStream inputStream = connection.getInputStream();// <===注意，实际发送请求的代码段就在这里

            //读取响应
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String lines;
            response = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                response.append(lines);
                response.append("\r\n");
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    //把接收到的東西喘成字串
    public static String returnResult(){
        return response.toString();
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
}
