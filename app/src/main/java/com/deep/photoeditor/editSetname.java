package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.net.Uri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Switch;

public class editSetname extends AppCompatActivity {
    Switch switchTemp;
    public Button btnNext;
    public Button btnFinishtemplate;
    public EditText setName;
    private static variable variable = new variable();
    private static api callApi = new api();

    public String templateShare = "1";
    public String template_id = "沒東西";

    private static Context mContext;

    Uri templateUri;
    public void init(){
        btnNext = (Button)findViewById(R.id.btnNext);
        btnFinishtemplate = (Button)findViewById(R.id.btnFinishtemplate);
        setName = (EditText)findViewById(R.id.setName);

        ContentResolver cr = this.getContentResolver();
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(templateUri));
            ImageView imageView = (ImageView) findViewById(R.id.imageView3);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.e("Exception", e.getMessage(),e);
        }

        //Switch
        switchTemp = findViewById(R.id.tempSwitch);
        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);
        //---switchTemp---
        switchTemp.setChecked(sharedPreferences.getBoolean("value",true));
        if (switchTemp.isChecked()) {
            templateShare = "1";
            Log.d("switchcheck","1");
        }else{
            templateShare = "0";
            Log.d("switchcheck","0");
        }
        switchTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchTemp.isChecked()) {
                    //當switch被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switchTemp.setChecked(true);
                    templateShare = "1";
                    Log.d("switchcheck","1");

                }else{
                    //當switch沒被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switchTemp.setChecked(false);
                    templateShare = "0";
                    Log.d("switchcheck","0");

                }
                variable.templateShareSetter(templateShare);
            }
        });

        //製作梗圖
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                post();
                Intent edit = new Intent(editSetname.this,EditImageActivity.class);
                startActivity(edit);
            }
        });

        //完成模板
        btnFinishtemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                post();
                Intent edit = new Intent(editSetname.this,editShare.class);
                startActivity(edit);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_setname);
        templateUri = variable.templateUriGetter();
        mContext = getApplicationContext();

        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        init();

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
    }
    public void post() {
        String templateName = setName.getText().toString();
        variable.templateNameSetter(templateName);
        try {
            callApi.post("http://140.131.115.99/api/txt/store",
                    "category_id="+variable.category_idGetter()+"&name=" + templateName +"&share=" +templateShare );
            Log.d("contextQQ","傳字串=" + callApi.returnString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String callBack = "";

        Log.d("contextTest","模板的filepath=" + getFilePathForN(mContext,templateUri));
        try {
            Log.d("contextQQ",callBack = callApi.multipartRequest("http://140.131.115.99/api/template/store","str="+
                    URLEncoder.encode(templateName, "UTF-8"),getFilePathForN(mContext,templateUri),"image" ));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("contextQQ","callBack="+callBack);
        int i = callBack.length();
        int searchLoc = callBack.indexOf("template_id");
        template_id = callBack.substring(searchLoc+13,i-3);
        Log.d("contextQQ","template_id="+template_id);
        variable.templateIDSetter(template_id);
    }
    private static String getFilePathForN(Context context, Uri uri) {
        try {
            Cursor returnCursor = context.getContentResolver().query(uri, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            returnCursor.moveToFirst();
            String name = (returnCursor.getString(nameIndex));
            File file = new File(context.getFilesDir(), name);
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            returnCursor.close();
            inputStream.close();
            outputStream.close();
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
