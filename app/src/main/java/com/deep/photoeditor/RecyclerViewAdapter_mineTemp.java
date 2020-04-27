package com.deep.photoeditor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

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
    public void onBindViewHolder(@NonNull MyViewHolder_mineTemp holder, int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        //將image用glide的方式呈現
        Glide.with(mContext)
                .load(mData.get(position).getMineTempImage())
                .apply(requestOptions)
                .into(holder.mineTempImage);
        holder.mineTempName.setText(mData.get(position).getMineTempName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder_mineTemp extends RecyclerView.ViewHolder {

        private TextView mineTempName;
        private ImageView mineTempImage;

        public MyViewHolder_mineTemp(@NonNull View itemView) {
            super(itemView);

            mineTempName = (TextView) itemView.findViewById(R.id.mineCardName);
            mineTempImage = (ImageView) itemView.findViewById(R.id.mineCardImage);
        }
    }
}
