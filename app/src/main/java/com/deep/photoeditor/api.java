package com.deep.photoeditor;

import android.util.Log;

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
import java.util.List;

public class api {
    private static BufferedReader reader;
    private static StringBuffer response;
    private static String header;

    public static void post(String httpMethodStr, String parameterData) throws Exception{
        try {
            URL url = new URL(httpMethodStr);//创建连接
            URLConnection urlConnection = url.openConnection();//声明一个抽象类URLConnection的引用urlConnection
            HttpURLConnection connection = (HttpURLConnection) urlConnection; //声明一个抽象类HttpURLConnection的引用connection
            connection.setRequestMethod("POST");//设定请求方式为"POST"，默认为"GET"
            connection.setRequestProperty("Authorization", "Bearer " + header);
            connection.setDoOutput(true);//设置是否向HttpUrlConnction输出，因为这个是POST请求，参数要放在http正文内，因此需要设为true，默认情况下是false
            connection.setDoInput(true);//设置是否向HttpUrlConnection读入，默认情况下是true
            connection.setUseCaches(false);//POST请求不能使用缓存（POST不能被缓存）
            connection.setConnectTimeout(20*1000);//设置连接主机超时（单位：毫秒）
            connection.setReadTimeout(20*1000);//设置从主机读取数据超时（单位：毫秒）
            connection.connect();
            DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
            byte[] t = parameterData.getBytes("utf-8");
            dataOutputStream.write(t);
            dataOutputStream.flush();
            dataOutputStream.close();
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
    //把接收到的東西變成字串
    public static String returnString(){
        return response.toString();
    }
    public static void setHeader(String header1){
       header = header1;
    }
    public static List cutString() {
        int x = 0;
        ArrayList a=new ArrayList();
        Log.d("Login", "New完了");

        a.add(returnString().indexOf("\""));//*第一個出現的索引位置
        Log.d("Login", Integer.toString((Integer)a.get(x)));

        while ((Integer)a.get(x)!= -1) {
            Log.d("Login", "進入迴圈");
            x+=1;

            a.add(returnString().indexOf("\"", (Integer)a.get(x-1)+1));//*從這個索引往後開始第一個出現的位置
            Log.d("Login", Integer.toString((Integer)a.get(x)));

        }
        a.remove(a.size()-1);
        Log.d("Login", "出迴圈");
        Log.d("Login", String.valueOf(a.size()));
        for(int i=0;i<a.size();i++){
            Log.d("Login","List的值：" + a.get(i).toString());
        }

        ArrayList list=new ArrayList();
        for(int i=0;i<a.size();i = i +2) {
            list.add(returnString().substring((Integer)a.get(i)+1,(Integer)a.get(i+1)));

        }
        Log.d("Login","List的值：" + list.get(0).toString());

        return list;
    }
}
