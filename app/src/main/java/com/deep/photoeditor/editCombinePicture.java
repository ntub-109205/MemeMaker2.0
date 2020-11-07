package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.deep.photoeditor.adpater.RecyclerViewAdapter;
import com.deep.photoeditor.adpater.RecyclerViewAdapter_layoutImage;

import java.util.ArrayList;
import java.util.List;

public class editCombinePicture extends AppCompatActivity {
    private final static String TAG = "editCombinePicture";
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter_layoutImage recyclerViewAdapter;
    List<Bitmap> mListImage;
    private ImageView layoutFullSingle;
    private ImageView layoutFullDouble;
    private ImageView layoutRightText;
    private ImageView layoutHorizontal;
    private Button btnNext;
    Uri TemplateUri;

    private static variable variable = new variable();
    private static ArrayList<Bitmap> templatesBitmap = new ArrayList<Bitmap>();

    public void init(){
        layoutFullSingle = (ImageView)findViewById(R.id.layoutFullSingle);
        layoutFullDouble = (ImageView)findViewById(R.id.layoutFullDouble);
        layoutRightText = (ImageView)findViewById(R.id.layoutRightText);
        layoutHorizontal = (ImageView)findViewById(R.id.layoutHorizontal);
        btnNext = (Button)findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                variable.templateUriSetter(TemplateUri);
                Intent edit = new Intent();
                edit.setClass(editCombinePicture.this, editSetname.class);
                startActivity(edit);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_combine_picture);
        init();
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        templatesBitmap = variable.templatesBitmapGetter();
        recyclerView = findViewById(R.id.layoutRecyclerview);
        recyclerViewAdapter = new RecyclerViewAdapter_layoutImage(this, templatesBitmap);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);


        layoutFullSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap mybitmap = puzzleMerge_vertical_noblank(fixImageSize(templatesBitmap));
                TemplateUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mybitmap, null,null));

            }
        });
        layoutFullDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap mybitmap = puzzleMerge_left_and_right(fixImageSize(templatesBitmap));
                TemplateUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mybitmap, null,null));

            }
        });
        layoutRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap mybitmap = puzzleMerge_vertical(fixImageSize(templatesBitmap));
                TemplateUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mybitmap, null,null));

            }
        });
        layoutHorizontal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap mybitmap = puzzleMerge_level(fixImageSize(templatesBitmap));
                TemplateUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mybitmap, null,null));

            }
        });

    }

    private Bitmap createBitmap(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        return bitmap;
    }
    /*
    public static ArrayList<Bitmap> fixImageQueue() {
        ArrayList<Bitmap> templatesBitmap2 = new ArrayList<Bitmap>();
        variable.BmpGetter();
        int size = variable.BmpCounterGetter().size();
        Log.d("imageQueue","size="+size);
        Log.d("imageQueue","index="+variable.BmpCounterGetter().indexOf(1));


//
//        for (int i = 0; i < size; i++) {
//           int index = variable.BmpCounterGetter().indexOf(i+1);
//           templatesBitmap2.add(variable.BmpGetter().get(index));
//        }
        return templatesBitmap2;
    }
    */

    public static ArrayList<Bitmap> fixImageSize(ArrayList<Bitmap> templatesBitmap1) {
        ArrayList<Bitmap> templatesBitmap2 = new ArrayList<Bitmap>();
        int size = templatesBitmap1.size();
        // 设置想要的大小
        int newWidth = templatesBitmap1.get(0).getWidth();
        int newHeight = templatesBitmap1.get(0).getHeight();

        for (int i = 0; i < size; i++) {
            int width = templatesBitmap1.get(i).getWidth();
            int height = templatesBitmap1.get(i).getHeight();
            // 计算缩放比例
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            Bitmap newbm = Bitmap.createBitmap(templatesBitmap1.get(i), 0, 0, width, height, matrix,
                    true);
            templatesBitmap2.add(newbm);
        }


        return templatesBitmap2;
    }






    //================================================================================
    //===================================以下拼圖======================================
    //================================================================================

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

}
