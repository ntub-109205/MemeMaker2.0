package com.deep.photoeditor.adpater;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deep.photoeditor.R;
import com.deep.photoeditor.layoutImage;

import java.util.List;

public class RecyclerViewAdapter_layoutImage extends RecyclerView.Adapter<RecyclerViewAdapter_layoutImage.MyViewHolder> {
    Context mContext;
    List<layoutImage> mData;

    public RecyclerViewAdapter_layoutImage(Context mContext, List<layoutImage> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_layout_image,parent,false);
        RecyclerViewAdapter_layoutImage.MyViewHolder vHolder = new RecyclerViewAdapter_layoutImage.MyViewHolder(v);
        return vHolder;
    }

    public int flag = 0;
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.layoutImageView.setImageResource(mData.get(position).getCombinePicture());
        holder.combinePic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //產生ImageView object
                ImageView imageView = new ImageView(mContext);
                if(flag == 0){
                    Log.d("flagXD", "flag = 0");
                    //讀取圖檔
                    imageView.setImageResource(R.drawable.circle);
                    RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.combineImage);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(90,90);
                    params.leftMargin = 280;
                    params.topMargin = 280;
                    relativeLayout.addView(imageView,params);
                    flag = 1;
                }else{
                    Log.d("flagXD", "flag = 1");

                    RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.combineImage);
                    relativeLayout.removeView(imageView);
                    flag = 0;
                }
            }
        });
    }

    @Override
    public int getItemCount() { return mData.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView layoutImageView;
        private RelativeLayout combinePic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            layoutImageView = (ImageView) itemView.findViewById(R.id.layoutImageView);
            combinePic = (RelativeLayout) itemView.findViewById(R.id.combineImage);

        }
    }
}
