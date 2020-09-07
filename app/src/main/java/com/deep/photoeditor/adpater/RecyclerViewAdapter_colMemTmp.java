package com.deep.photoeditor.adpater;

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
import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.TemplateInfoActivity;
import com.deep.photoeditor.colMemTmp;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewAdapter_colMemTmp extends RecyclerView.Adapter<RecyclerViewAdapter_colMemTmp.MyViewHolder> {
    Context mContext;
    List<colMemTmp> mData;


    public RecyclerViewAdapter_colMemTmp(Context mContext, List<colMemTmp> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_collect_template,parent,false);
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
                .into(holder.imgView);
        holder.imgName.setText(mData.get(position).getTempName());
        //  holder.fireNum.setText(String.valueOf(mData.get(position).getUsedSum()));

        holder.item_meme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mData.get(position));

                Toast.makeText(mContext, mData.get(position).getTempName(), Toast.LENGTH_SHORT).show();
//                Intent edit = new Intent();
//
//                edit.putExtra("temp_name", mData.get(position).getTempName());
//                edit.setClass(mContext, TemplateInfoActivity.class);
//                mContext.startActivity(edit);
                Intent intent = new Intent(mContext, TemplateInfoActivity.class);
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

        private TextView hashTag;
        private ImageView collect;
        private TextView imgName;
        private RelativeLayout item_meme;
        private ImageView imgView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_meme = (RelativeLayout) itemView.findViewById(R.id.template_item_col);
            //imgName = (TextView) itemView.findViewById(R.id.cardName);
            imgView = (ImageView) itemView.findViewById(R.id.cardImage);
            imgName = (TextView) itemView.findViewById(R.id.cardName);
            collect = (ImageView) itemView.findViewById(R.id.bookmarkCheck);
        }
    }
}
