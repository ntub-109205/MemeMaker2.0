package com.deep.photoeditor;

import android.net.ParseException;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class api {
    private static BufferedReader reader;
    private static StringBuffer response;
    private static String header;
    private static String boundary =  "*****"+Long.toString(System.currentTimeMillis())+"*****";

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
        Log.d("Login3", "New完了");

        a.add(returnString().indexOf("\""));//*第一個出現的索引位置
        Log.d("Login3", Integer.toString((Integer)a.get(x)));

        while ((Integer)a.get(x)!= -1) {
            Log.d("Login3", "進入迴圈");
            x+=1;

            a.add(returnString().indexOf("\"", (Integer)a.get(x-1)+1));//*從這個索引往後開始第一個出現的位置
            Log.d("Login3", Integer.toString((Integer)a.get(x)));

        }
        a.remove(a.size()-1);
        Log.d("Login3", "出迴圈");
        Log.d("Login3", String.valueOf(a.size()));
        for(int i=0;i<a.size();i++){
            Log.d("Login3","List的值：" + a.get(i).toString());
        }





        ArrayList list=new ArrayList();
        for(int i=0;i<a.size();i = i +2) {
            list.add(returnString().substring((Integer)a.get(i)+1,(Integer)a.get(i+1)));

        }
        Log.d("Login","List的值：" + list.get(0).toString());

        return list;
    }

    public String multipartRequest(String urlTo, String post, String filepath, String filefield) throws ParseException, IOException {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        InputStream inputStream = null;

        String twoHyphens = "--";
        String boundary =  "*****"+Long.toString(System.currentTimeMillis())+"*****";
        String lineEnd = "\r\n";

        String result = "";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1*1024*1024;

        String[] q = filepath.split("/");
        int idx = q.length - 1;

        try {
            File file = new File(filepath);
            FileInputStream fileInputStream = new FileInputStream(file);

            URL url = new URL(urlTo);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + header);
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//設定訊息的型別
//            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
//            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
//            connection.setRequestProperty("Accept", "application/json");
//            connection.setRequestProperty("Accept-Charset", "UTF-8");
            //connection.setRequestProperty("content-type", "application/json;  charset=utf-8");
            //Writer writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
//            byte[] t = post.getBytes("utf-8");

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
//            outputStream.writeBytes("Content-Disposition: form-data; name=\"" + filefield + "\"; filename=\"" + t +"\"" + lineEnd);

            outputStream.writeBytes("Content-Disposition: form-data; name=\"" + filefield + "\"; filename=\"" + q[idx] +"\"" + lineEnd);
            outputStream.writeBytes("Content-Type: image/jpeg" + lineEnd);
            outputStream.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);
            outputStream.writeBytes(lineEnd);

//            writer.write(twoHyphens + boundary + lineEnd);
//            writer.write("Content-Disposition: form-data; name=\"" + filefield + "\"; filename=\"" + q[idx] +"\"" + lineEnd);
//            writer.write("Content-Type: image/jpeg" + lineEnd);
//            writer.write("Content-Transfer-Encoding: binary" + lineEnd);
//            writer.write(lineEnd);


            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            while(bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                //writer.write(String.valueOf(buffer), 0, bufferSize);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            outputStream.writeBytes(lineEnd);
            //writer.write(lineEnd);
            Log.d("contextQQ","莫名其妙");

//            String[] posts =  post.split("&");
//            int max = posts.length;
//            for(int i=0; i<max;i++) {
//                outputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                String[] kv = posts[i].split("=");
//                outputStream.writeBytes("Content-Disposition: form-data; name=\"" + kv[0] + "\"" + lineEnd);
//                outputStream.writeBytes("Content-Type: text/plain"+lineEnd);
//                outputStream.writeBytes(lineEnd);
//
//                outputStream.writeBytes(kv[1]);
//                outputStream.writeBytes(lineEnd);

//                writer.write(twoHyphens + boundary + lineEnd);
//                String[] kv = posts[i].split("=");
//                writer.write("Content-Disposition: form-data; name=\"" + kv[0] + "\"" + lineEnd);
//                writer.write("Content-Type: text/plain"+lineEnd);
//                writer.write(lineEnd);
//
//                writer.write(kv[1]);
//                writer.write(lineEnd);


//            }
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

           // writer.write(twoHyphens + boundary + twoHyphens + lineEnd);
            Log.d("contextQQ","寫完");
            inputStream = connection.getInputStream();
            Log.d("contextQQ","取得");
            result = this.convertStreamToString(inputStream);
            Log.d("contextQQ",result);

            fileInputStream.close();
            inputStream.close();
//            writer.flush();
//            writer.close();
            outputStream.flush();
            outputStream.close();

            return result;
        } catch(Exception e) {
            Log.e("MultipartRequest","Multipart Form Upload Error");
            e.printStackTrace();
            return "error";
        }
    }

    private String convertStreamToString(InputStream is) throws IOException {
        Log.d("contextQQ","近來");

//        BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));
//
//        Log.d("contextQQ","1");
//
//        StringBuilder sb = new StringBuilder();
//        Log.d("contextQQ","2");
//
//        String line = null;
//        Log.d("contextQQ","3");
//读取响应
        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        String lines;
        response = new StringBuffer("");
        while ((lines = reader.readLine()) != null) {
            response.append(lines);
            response.append("\r\n");
        }
//        try {
//            while ((line = reader.readLine()) != null) {
//                Log.d("contextQQ","4");
//
//                sb.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                Log.d("contextQQ","5");
//
//                is.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        Log.d("contextQQ",returnString());

        return returnString();
    }

}
