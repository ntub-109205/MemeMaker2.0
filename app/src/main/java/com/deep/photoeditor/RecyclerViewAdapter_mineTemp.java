package com.deep.photoeditor;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewAdapter_mineTemp extends RecyclerView.Adapter<RecyclerViewAdapter_mineTemp.MyViewHolder_mineTemp> {
    Context mContext;
    List<mineTemplate> mData;

    public RecyclerViewAdapter_mineTemp(Context mContext, List<mineTemplate> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder_mineTemp onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_minetemplate,parent,false);
        RecyclerViewAdapter_mineTemp.MyViewHolder_mineTemp vHolder = new RecyclerViewAdapter_mineTemp.MyViewHolder_mineTemp(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder_mineTemp holder, final int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        //將image用glide的方式呈現
        Glide.with(mContext)
                .load(mData.get(position).getMineTempImage())
                .apply(requestOptions)
                .into(holder.mineTempImage);
        holder.mineTempName.setText(mData.get(position).getMineTempName());
        holder.fireNum.setText(String.valueOf(mData.get(position).getUsedSum()));

        //新增圖片傳值到其他Activity
        holder.mineTemp_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mData.get(position));

                Toast.makeText(mContext, mData.get(position).getMineTempName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, TemplateInfoActivity.class);
                intent.putExtra("temp_url", mData.get(position).getMineTempImage());
                intent.putExtra("temp_name", mData.get(position).getMineTempName());
                intent.putExtra("user_name", mData.get(position).getUserName());
                intent.putExtra("used_sum", mData.get(position).getUsedSum());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder_mineTemp extends RecyclerView.ViewHolder {

        private TextView mineTempName;
        private ImageView mineTempImage;
        private RelativeLayout mineTemp_item;
        private TextView fireNum;

        public MyViewHolder_mineTemp(@NonNull View itemView) {
            super(itemView);

            mineTempName = (TextView) itemView.findViewById(R.id.mineCardName);
            mineTempImage = (ImageView) itemView.findViewById(R.id.mineCardImage);
            mineTemp_item = (RelativeLayout) itemView.findViewById(R.id.mineTemp_item);
            fireNum = (TextView) itemView.findViewById(R.id.itemFireNum);
        }
    }
}
