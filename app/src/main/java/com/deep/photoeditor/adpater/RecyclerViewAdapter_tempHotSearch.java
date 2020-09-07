package com.deep.photoeditor.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deep.photoeditor.R;
import com.deep.photoeditor.tagHotSearch;

import java.util.List;

public class RecyclerViewAdapter_tempHotSearch extends RecyclerView.Adapter<RecyclerViewAdapter_tempHotSearch.MyViewHolder> {
    Context mContext;
    List<tagHotSearch> mData;

    public RecyclerViewAdapter_tempHotSearch(Context mContext, List<tagHotSearch> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_tempsearch,parent,false);
        RecyclerViewAdapter_tempHotSearch.MyViewHolder vHolder = new RecyclerViewAdapter_tempHotSearch.MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.serialImage.setImageResource(mData.get(position).getSerialImage());
        holder.tempName.setText(mData.get(position).getTempName());
        holder.usedSum.setText(String.valueOf(mData.get(position).getUsedSum()));
        holder.tempSearch_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout tempSearch_item;
        private ImageView serialImage;
        private TextView tempName;
        private TextView usedSum;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tempSearch_item = (LinearLayout) itemView.findViewById(R.id.tempSearch_item);
            serialImage = (ImageView) itemView.findViewById(R.id.serialNo);
            tempName = (TextView) itemView.findViewById(R.id.tempName);
            usedSum = (TextView) itemView.findViewById(R.id.usedSum);
        }
    }

}
