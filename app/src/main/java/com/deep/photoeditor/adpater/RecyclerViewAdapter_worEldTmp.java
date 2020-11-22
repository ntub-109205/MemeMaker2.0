package com.deep.photoeditor.adpater;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.WorkMemeTempInfoActivity;
import com.deep.photoeditor.memeTemplate;
import com.deep.photoeditor.worMemTmp;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewAdapter_worEldTmp extends RecyclerView.Adapter<RecyclerViewAdapter_worEldTmp.MyViewHolder> {
    Context mContext;
    List<worMemTmp> mData;


    public RecyclerViewAdapter_worEldTmp(Context mContext, List<worMemTmp> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_template,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        //將image用glide的方式呈現
        Glide.with(mContext)
                .load(mData.get(position).getTempImage())
                .apply(requestOptions)
                .into(holder.tempImage);
        holder.tempName.setText(mData.get(position).getTempName());
        holder.fireNum.setText(String.valueOf(mData.get(position).getUsedSum()));

        holder.item_template.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mData.get(position));

                Toast.makeText(mContext, mData.get(position).getTempName(), Toast.LENGTH_SHORT).show();
//
//                edit.putExtra("temp_name", mData.get(position).getTempName());
//                edit.setClass(mContext, TemplateInfoActivity.class);
//                mContext.startActivity(edit);
                Intent intent = new Intent(mContext, WorkMemeTempInfoActivity.class);
                intent.putExtra("temp_id", mData.get(position).getTemp_id());
                intent.putExtra("temp_url", mData.get(position).getTempImage());
                intent.putExtra("temp_name", mData.get(position).getTempName());
                intent.putExtra("user_name", mData.get(position).getUserName());
                intent.putExtra("share", mData.get(position).getShared());
                mContext.startActivity(intent);
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
        private RelativeLayout item_template;
        private Switch share;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_template = (RelativeLayout) itemView.findViewById(R.id.template_item_id);
            tempName = (TextView) itemView.findViewById(R.id.cardName);
            tempImage = (ImageView) itemView.findViewById(R.id.cardImage);
            fireNum = (TextView) itemView.findViewById(R.id.itemFireNum);
            share = (Switch) itemView.findViewById(R.id.tempSwitch);
        }
    }
}
