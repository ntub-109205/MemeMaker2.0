package com.deep.photoeditor.adpater;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.TemplateInfoActivity;
import com.deep.photoeditor.tagHotSearch;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

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
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        //將image用glide的方式呈現
        Glide.with(mContext)
                .load(mData.get(position).getTempImage())
                .apply(requestOptions)
                .into(holder.tempImage);
        holder.tempName.setText(mData.get(position).getTempName());
        holder.usedSum.setText(String.valueOf(mData.get(position).getUsedSum()));
        holder.serNo.setImageResource(mData.get(position).getSerialImage());
        holder.tempSearch_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mData.get(position));
                Toast.makeText(mContext, mData.get(position).getTempName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, TemplateInfoActivity.class);
                intent.putExtra("temp_id", mData.get(position).getTempId());
                intent.putExtra("temp_url", mData.get(position).getTempImage());
                intent.putExtra("temp_name", mData.get(position).getTempName());
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout tempSearch_item;
        private ImageView tempImage;
        private ImageView serNo;
        private TextView tempName;
        private TextView usedSum;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tempSearch_item = (RelativeLayout) itemView.findViewById(R.id.tempSearch_item);
            tempImage = (ImageView) itemView.findViewById(R.id.tempImage);
            serNo = (ImageView) itemView.findViewById(R.id.serNo);
            tempName = (TextView) itemView.findViewById(R.id.tempName);
            usedSum = (TextView) itemView.findViewById(R.id.usedSum);
        }
    }

}
