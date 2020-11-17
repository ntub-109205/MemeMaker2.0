package com.deep.photoeditor.adpater;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.deep.photoeditor.image_selector.multi_image_selector.bean.Image;
import com.deep.photoeditor.layoutImage;
import com.deep.photoeditor.R;
import com.deep.photoeditor.variable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class RecyclerViewAdapter_layoutImage extends RecyclerView.Adapter<RecyclerViewAdapter_layoutImage.ImageViewHolder> {
    private Context mContext;
    private ArrayList<Bitmap> mLayoutImageList;
    private HashMap<View, ImageView> mImageViewMap = new HashMap<View, ImageView>();
    private HashMap<Bitmap, Integer> ImageQueue = new HashMap<Bitmap, Integer>();

    private final static String TAG = "Recycler_layoutImage";
    private List<ImageView> mImageViewList;
    ArrayList<Bitmap> Bmp = new ArrayList<Bitmap>();
    ArrayList<Integer> BmpCounter = new ArrayList<Integer>();
    private static variable variable = new variable();

    public RecyclerViewAdapter_layoutImage(Context context, ArrayList<Bitmap> layoutImageList) {
        this.mContext = context;
        this.mLayoutImageList = layoutImageList;
        mImageViewList = new ArrayList<ImageView>();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_image,parent,false);
        RecyclerViewAdapter_layoutImage.ImageViewHolder viewHolder = new RecyclerViewAdapter_layoutImage.ImageViewHolder(view);
        return viewHolder;
    }

    private Bitmap getBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    private int mCounter = 1;
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.layoutImageView.setImageBitmap(mLayoutImageList.get(position));
        Bmp = variable.templatesBitmapGetter();

        //Bmp = variable.BmpGetter();
        holder.combinePic.setOnClickListener(new View.OnClickListener() {
            ImageView imageView = new ImageView(mContext);
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(View view) {
                //Bitmap bitmap =((BitmapDrawable)view.getDrawable()).getBitmap();
               // Log.d("check_image", "點選的圖片:"+bitmap.toString());


                Log.d(TAG, "[SelectorView] ImageView onClick, view = " + view.toString());
                RelativeLayout relativeLayout = holder.combinePic;
                // for adding or removing the selector view.
                if (mImageViewMap.get(view) == null) {

//                    imageView.setDrawingCacheEnabled(true);
//
//                    if (Bmp.contains(imageView.getDrawingCache())){
//                        int idx = Bmp.indexOf(imageView.getDrawingCache());
//                        Log.d("Combinetest","idx="+idx);
//                        Bmp.remove(idx);
//                        BmpCounter.remove(idx);
//
//                        Bmp.add(imageView.getDrawingCache());
//                        BmpCounter.add(mCounter);
//                    }else {
//                        Bmp.add(imageView.getDrawingCache());
//                        BmpCounter.add(mCounter);
//                    }
//                    imageView.setDrawingCacheEnabled(false);
//                    variable.BmpSetter(Bmp);
//                    variable.BmpCounterSetter(BmpCounter);
                    mImageViewMap.put(view, imageView);
                    mImageViewList.add(imageView);
                    addSelectorView(relativeLayout, imageView, mCounter);
                    mCounter++;
                } else {
                    mImageViewMap.remove(view);
                    mImageViewList.remove(imageView);
                    removeSelectorView(relativeLayout, imageView);
                    updateSelectorView(relativeLayout, imageView);
                    mCounter--;
                }
                Log.d("check_image", "imageView="+((BitmapDrawable)holder.layoutImageView.getDrawable()).getBitmap().toString());
                //Log.d("check_image", "Bmp.get(0)="+Bmp.get(0).toString());
                Log.d("check_image", "mCounter="+(mCounter-1));
                Bitmap bitmap = ((BitmapDrawable)holder.layoutImageView.getDrawable()).getBitmap();
                int counter = mCounter-1;


                if(ImageQueue.containsKey(bitmap)){
                    ImageQueue.remove(bitmap);
                    Iterator iterator1 = ImageQueue.entrySet().iterator();
                    while (iterator1.hasNext()){
                        Map.Entry entry = (Map.Entry) iterator1.next();
                        Bitmap key = (Bitmap) entry.getKey();
                        Integer value = (Integer) entry.getValue();
                        if(counter<=value) {
                            ImageQueue.replace(key, value - 1);
                        }
                        Log.d("check_image", "iterator1="+key.toString()+"="+ImageQueue.get(key));
                    }
                }else{
                    ImageQueue.put(bitmap,counter);

                }
                //ImageQueue.put(bitmap,counter);
                Iterator iterator1 = ImageQueue.entrySet().iterator();
                Log.d("check_image", "======================================================================");
                while (iterator1.hasNext()){
                    Map.Entry entry = (Map.Entry) iterator1.next();
                    Bitmap key = (Bitmap) entry.getKey();
                    Integer value = (Integer) entry.getValue();
                    Log.d("check_image", "iterator1="+key.toString()+"="+value);
                }
                Log.d("check_image", "======================================================================");
                variable.ImageQueueSetter(ImageQueue);

            }
        });

    }


    private void updateSelectorView(RelativeLayout relativeLayout, ImageView imageView) {
        for (ImageView view : mImageViewList) {
            removeSelectorView(relativeLayout, view);
        }
        //variable.BmpSetter(mImageViewList);
        int size = mImageViewList.size();

        for (int i = 0; i < size; ++i) {
            ImageView view = mImageViewList.get(i);
            addSelectorView(relativeLayout, view, i+1);
        }
    }

    private void addSelectorView(RelativeLayout relativeLayout, ImageView imageView, int counter) {
        Log.d(TAG, "[SelectorView] addSelectorView, imageView = " + imageView.toString());
        //load image resources
        setSelectorImage(imageView, counter);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(90,90);
        params.leftMargin = 280;
        params.topMargin = 280;
        try {
            relativeLayout.addView(imageView, params);
        } catch (Exception e) {
            // do nothing.
        }
    }

    private void removeSelectorView(RelativeLayout relativeLayout, ImageView imageView) {
        Log.d(TAG, "[SelectorView] removeSelectorView, imageView = " + imageView.toString());
        relativeLayout.removeView(imageView);
    }

    private void setSelectorImage(ImageView view, int counter){
        switch (counter) {
            case 1:
                view.setImageResource(R.drawable.selectone);
                break;
            case 2:
                view.setImageResource(R.drawable.selecttwo);
                break;
            case 3:
                view.setImageResource(R.drawable.selectthree);
                break;
            case 4:
                view.setImageResource(R.drawable.selectfour);
                break;
            case 5:
                view.setImageResource(R.drawable.selectfive);
                break;
            case 6:
                view.setImageResource(R.drawable.selectsix);
                break;
            case 7:
                view.setImageResource(R.drawable.selectseven);
                break;
            case 8:
                view.setImageResource(R.drawable.selecteight);
                break;
            case 9:
                view.setImageResource(R.drawable.selectnine);
                break;
            case 10:
                view.setImageResource(R.drawable.selectten);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mLayoutImageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView layoutImageView;
        private RelativeLayout combinePic;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutImageView = (ImageView) itemView.findViewById(R.id.layoutImageView);
            combinePic = (RelativeLayout) itemView.findViewById(R.id.combineImage);

        }
    }
}
