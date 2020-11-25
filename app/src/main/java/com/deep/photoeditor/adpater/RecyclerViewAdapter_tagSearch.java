package com.deep.photoeditor.adpater;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.SearchTagInfoActivity;
import com.deep.photoeditor.tagSearch;
import com.deep.photoeditor.variable;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewAdapter_tagSearch extends RecyclerView.Adapter<RecyclerViewAdapter_tagSearch.MyViewHolder> {
    private static com.deep.photoeditor.variable variable = new variable();
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
        holder.txtTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tagName = mData.get(position).getTag();
                variable.tagNameSetter(tagName.substring(1,tagName.length()));
                Log.d(TAG, "onClick: clicked on: " + mData.get(position));
                Intent intent = new Intent(mContext, SearchTagInfoActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout tagSearch_item;
        private TextView txtTag;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTag = (TextView) itemView.findViewById(R.id.txtTag);
        }
    }
}
