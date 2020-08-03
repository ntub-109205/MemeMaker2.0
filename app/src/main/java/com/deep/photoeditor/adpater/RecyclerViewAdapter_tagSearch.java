package com.deep.photoeditor.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deep.photoeditor.R;
import com.deep.photoeditor.tagSearch;

import java.util.List;

public class RecyclerViewAdapter_tagSearch extends RecyclerView.Adapter<RecyclerViewAdapter_tagSearch.MyViewHolder> {
    Context mContext;
    List<tagSearch> mData;

    public RecyclerViewAdapter_tagSearch(Context mContext, List<tagSearch> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_tagsearch,parent,false);
        RecyclerViewAdapter_tagSearch.MyViewHolder vHolder = new RecyclerViewAdapter_tagSearch.MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtTag.setText(mData.get(position).getTag());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTag = (TextView) itemView.findViewById(R.id.txtTag);
        }
    }
}
