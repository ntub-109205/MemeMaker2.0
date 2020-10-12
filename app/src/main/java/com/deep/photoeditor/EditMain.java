package com.deep.photoeditor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.deep.photoeditor.adpater.PageAdapter;
import com.deep.photoeditor.fragment.edittab1;
import com.deep.photoeditor.fragment.edittab2;
import com.google.android.material.tabs.TabLayout;
import android.net.Uri;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EditMain extends AppCompatActivity {
    public Button btnAddMeme;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;
    private static variable variable = new variable();
    private static api callApi = new api();


    int PICK_IMAGE_MULTIPLE = 1;
    String imageEncoded;
    List<String> imagesEncodedList;


    Uri TemplateUri;
    public void init(){
        btnAddMeme = (Button)findViewById(R.id.btnAddmeme);
        tabLayout = (TabLayout)findViewById(R.id.editTablayout);
        viewPager = (ViewPager)findViewById(R.id.editViewPager);
        pagerAdapter = new PageAdapter(getSupportFragmentManager());
        pagerAdapter.AddFragment(new edittab1(),"熱門模板");
        pagerAdapter.AddFragment(new edittab2(),"我的模板");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        btnAddMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent, 1);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), PICK_IMAGE_MULTIPLE);

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_main);
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {

            Log.d("getXD",callApi.get("http://140.131.115.99/api/profile"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        init();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            TemplateUri = data.getData();
//            variable.templateUriSetter(TemplateUri);
//            Intent edit = new Intent();
//            edit.setClass(EditMain.this, editSetname.class);
//            startActivity(edit);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    try {
        // When an Image is picked
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK
                && null != data) {
            // Get the Image from data

            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            imagesEncodedList = new ArrayList<String>();
            if(data.getData()!=null){

                TemplateUri = data.getData();
                variable.templateUriSetter(TemplateUri);
                Intent edit = new Intent();
                edit.setClass(EditMain.this, editSetname.class);
                startActivity(edit);

            } else {
                if (data.getClipData() != null) {

                    ClipData mClipData = data.getClipData();
                    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
                    ArrayList<Bitmap> mArrayBitmap = new ArrayList<Bitmap>();

                    ContentResolver cr = this.getContentResolver();

                    for (int i = 0; i < mClipData.getItemCount(); i++) {

                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                        mArrayBitmap.add(getBitmap(cr,uri));

                    }
                    variable.templatesBitmapSetter(mArrayBitmap);
                    Bitmap mybitmap = puzzleMerge_vertical_noblank(mArrayBitmap);
                    TemplateUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mybitmap, null,null));

                    variable.templateUriSetter(TemplateUri);
                    Intent edit = new Intent();
                    edit.setClass(EditMain.this, editCombinePicture.class);
                    startActivity(edit);
                    Log.v("LOG_TAG","Selected Images" + mArrayUri.size());
                }
            }
        } else {
            Toast.makeText(this,"You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }
    } catch (Exception e) {
        Toast.makeText(this,"Something went wrong", Toast.LENGTH_LONG)
                .show();
    }

    super.onActivityResult(requestCode, resultCode, data);
}

//    ContentResolver cr = this.getContentResolver();

    public static final Bitmap getBitmap(ContentResolver cr, Uri url)
            throws FileNotFoundException, IOException {
        InputStream input = cr.openInputStream(url);
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        input.close();
        return bitmap;
    }
    public static Bitmap puzzleMerge_vertical(ArrayList<Bitmap> mArrayBitmap) {
        int size = mArrayBitmap.size();
        int width1 = mArrayBitmap.get(0).getWidth()*2;
        int height1 = 0;

        for (int i = 0; i < size; i++) {
            height1 +=mArrayBitmap.get(i).getHeight();
        }

        Bitmap bitmap = Bitmap.createBitmap(width1, height1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        int top=0;
        for (int i = 0; i < size; i++) {
            canvas.drawBitmap( mArrayBitmap.get(i), 0, top, null);
            top+=mArrayBitmap.get(i).getHeight();
        }

        return bitmap;
    }
    public static Bitmap puzzleMerge_vertical_noblank(ArrayList<Bitmap> mArrayBitmap) {
        int size = mArrayBitmap.size();
        int width1 = mArrayBitmap.get(0).getWidth();
        int height1 = 0;

        for (int i = 0; i < size; i++) {
            height1 +=mArrayBitmap.get(i).getHeight();
        }

        Bitmap bitmap = Bitmap.createBitmap(width1, height1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        int top=0;
        for (int i = 0; i < size; i++) {
            canvas.drawBitmap( mArrayBitmap.get(i), 0, top, null);
            top+=mArrayBitmap.get(i).getHeight();
        }

        return bitmap;
    }
    public static Bitmap puzzleMerge_level(ArrayList<Bitmap> mArrayBitmap) {
        int size = mArrayBitmap.size();
        int height1 = mArrayBitmap.get(0).getHeight() + mArrayBitmap.get(0).getHeight()/2;
        int width1 = 0;

        for (int i = 0; i < size; i++) {
            width1 +=mArrayBitmap.get(i).getWidth();
        }

        Bitmap bitmap = Bitmap.createBitmap(width1, height1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        int left=0;
        for (int i = 0; i < size; i++) {
            canvas.drawBitmap( mArrayBitmap.get(i), left, 0, null);
            left+=mArrayBitmap.get(i).getWidth();
        }

        return bitmap;
    }
    public static Bitmap puzzleMerge_left_and_right(ArrayList<Bitmap> mArrayBitmap) {
        int size = mArrayBitmap.size();
        int width1 = mArrayBitmap.get(0).getWidth()*2;
        int height1 = 0;
        int h = 0;
        //計算要多少的高度
        if (size % 2 == 0){h=size;}
        else{h=(size+1);}
        height1 = mArrayBitmap.get(0).getHeight()*h/2;

        Bitmap bitmap = Bitmap.createBitmap(width1, height1, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0){
                canvas.drawBitmap( mArrayBitmap.get(i), 0, mArrayBitmap.get(i).getHeight()*i/2, null);
            }else{
                canvas.drawBitmap( mArrayBitmap.get(i), mArrayBitmap.get(i-1).getWidth(), mArrayBitmap.get(i).getHeight()*(i-1)/2, null);
            }
        }

        return bitmap;
    }
}
