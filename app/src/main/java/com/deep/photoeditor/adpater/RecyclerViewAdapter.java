package com.deep.photoeditor.adpater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.deep.photoeditor.EditImageActivity;
import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.TemplateInfoActivity;
import com.deep.photoeditor.hotTemplate;
import com.deep.photoeditor.variable;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context mContext;
    List<hotTemplate> mData;
    private static com.deep.photoeditor.variable variable = new variable();

    public RecyclerViewAdapter(Context mContext, List<hotTemplate> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_hottemplate,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

//        holder.tempName.setText(mData.get(position).getTempName());
//        holder.tempImage.setImageResource(mData.get(position).getTempImage());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        //將image用glide的方式呈現
        Glide.with(mContext)
                .load(mData.get(position).getTempImage())
                .apply(requestOptions)
                .into(holder.tempImage);
        holder.tempName.setText(mData.get(position).getTempName());
        holder.fireNum.setText(String.valueOf(mData.get(position).getUsedSum()));

        //新增圖片傳值到其他Activity
        holder.hotTemp_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mData.get(position));
                Log.d("imageUrl", "imageUrl=" + mData.get(position).getTempImage());
                variable.templateImageSetter(getBitmap(mData.get(position).getTempImage()));
                variable.templateIDSetter(mData.get(position).getTemp_id());
                variable.useMyImageSetter(Boolean.FALSE);
                Intent edit = new Intent(mContext, EditImageActivity.class);
                mContext.startActivity(edit);

                Toast.makeText(mContext, mData.get(position).getTempName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tempName;
        private ImageView tempImage;
        private TextView fireNum;
        private RelativeLayout hotTemp_item;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tempName = (TextView) itemView.findViewById(R.id.cardName);
            tempImage = (ImageView) itemView.findViewById(R.id.cardImage);
            hotTemp_item = (RelativeLayout) itemView.findViewById(R.id.hotTemp_item);
            fireNum = (TextView) itemView.findViewById(R.id.itemFireNum);
        }


    }
    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }
}
