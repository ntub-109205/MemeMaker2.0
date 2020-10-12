package com.deep.photoeditor.adpater;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.deep.photoeditor.layoutImage;
import com.deep.photoeditor.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecyclerViewAdapter_layoutImage extends RecyclerView.Adapter<RecyclerViewAdapter_layoutImage.ImageViewHolder> {
    private Context mContext;
    private List<Bitmap> mLayoutImageList;
    private HashMap<View, ImageView> mImageViewMap = new HashMap<View, ImageView>();

    public RecyclerViewAdapter_layoutImage(Context context, List<Bitmap> layoutImageList) {
        this.mContext = context;
        this.mLayoutImageList = layoutImageList;
    }
    private static final int NUMBER1 = 1;

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_image,parent,false);
        RecyclerViewAdapter_layoutImage.ImageViewHolder viewHolder = new RecyclerViewAdapter_layoutImage.ImageViewHolder(view);
        return viewHolder;
    }

    private final static String TAG = "Recycler_layoutImage";
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.layoutImageView.setImageBitmap(mLayoutImageList.get(position));
        holder.combinePic.setOnClickListener(new View.OnClickListener() {
            ImageView imageView = new ImageView(mContext);
            public void onClick(View view) {
                Log.d(TAG, "[SelectorView] ImageView onClick, view = " + view.toString());
                RelativeLayout relativeLayout = holder.combinePic;
                // for adding or removing the selector view.
                if (mImageViewMap.get(view) == null) {
                    mImageViewMap.put(view, imageView);
                    addSelectorView(relativeLayout, imageView);
                } else {
                    removeSelectorView(relativeLayout, imageView);
                    mImageViewMap.remove(view);
                }
            }
        });
    }

    private void addSelectorView(RelativeLayout relativeLayout, ImageView imageView) {
        Log.d(TAG, "[SelectorView] addSelectorView, imageView = " + imageView.toString());

        //load image resources
        imageView.setImageResource(R.drawable.circle);
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
