package com.deep.photoeditor.adpater;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.deep.photoeditor.PublicMeme;
import com.deep.photoeditor.R;
import com.deep.photoeditor.WorPublicMeme;
import com.deep.photoeditor.activity.PublicMemeInfoActivity;
import com.deep.photoeditor.activity.WorkMemeInfoActivity;
import com.deep.photoeditor.api;
import com.wx.goodview.GoodView;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewAdapter_worEld extends RecyclerView.Adapter<RecyclerViewAdapter_worEld.MyViewHolder> {
    Context mContext;
    List<WorPublicMeme> mData;
    private static api callApi = new api();

    public RecyclerViewAdapter_worEld(Context mContext, List<WorPublicMeme> mData) {
        this.mContext = mContext;
        this.mData = mData;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_meme,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        //將image用glide的方式呈現
        Glide.with(mContext)
                .load(mData.get(position).getMemeImage())
                .apply(requestOptions)
                .into(holder.memeImage);
        holder.hashTag.setText(mData.get(position).getHashTag());
        holder.likeNum.setText(String.valueOf(mData.get(position).getLikeSum()));
        //判斷愛心顏色
        if(Integer.parseInt(String.valueOf(mData.get(position).getThumb())) == 0) {
            holder.like.setImageResource(R.drawable.like_gray);
        } else {
            holder.like.setImageResource(R.drawable.like_checked);
        }

        holder.like.setOnClickListener(new View.OnClickListener() {
            //            @Override
            int thumb = Integer.parseInt(String.valueOf(mData.get(position).getThumb()));
            int likeSum = Integer.parseInt(String.valueOf(mData.get(position).getLikeSum()));
            public void onClick(View view) {
                GoodView mGoodView;
                mGoodView = new GoodView(mContext);
                Log.d(TAG, "onClick: clicked on: " + mData.get(position));
                if(thumb == 0){
                    ((ImageView) view).setImageResource(R.drawable.like_checked);
                    mGoodView. setTextInfo("+1", Color.parseColor("#f66467"), 12);
                    mGoodView.show(view);
                    //改旁邊的顯示
                    holder.likeNum.setText(String.valueOf(likeSum +1));
                    likeSum += 1;
                    thumb = 1;
                } else {
                    ((ImageView) view).setImageResource(R.drawable.like_gray);
                    mGoodView. setTextInfo("-1", Color.parseColor("#999da4"), 12);
                    mGoodView.show(view);
                    //改旁邊的顯示
                    holder.likeNum.setText(String.valueOf(likeSum- 1));
                    likeSum -= 1;
                    thumb = 0;
                }

                try {
                    callApi.post("http://140.131.115.99/api/meme/thumb","meme_id=" + Integer.parseInt(String.valueOf(mData.get(position).getMemeId())));
//                    callApi.get("http://140.131.115.99/api/meme/show/1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("thumb", callApi.returnString());
            }
        });

        holder.item_meme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mData.get(position));

                Toast.makeText(mContext, mData.get(position).getHashTag(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, WorkMemeInfoActivity.class);
                intent.putExtra("temp_id", mData.get(position).getTempId());

                intent.putExtra("meme_url", mData.get(position).getMemeImage());
                intent.putExtra("hashTag", mData.get(position).getHashTag());
                intent.putExtra("user_name", mData.get(position).getUserName());
                intent.putExtra("like_sum", mData.get(position).getLikeSum());
                intent.putExtra("shared",mData.get(position).getShared());
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
        private ImageView memeImage;
        private TextView likeNum;
        private RelativeLayout item_meme;
        private ImageView like;
        private Switch shared;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_meme = (RelativeLayout) itemView.findViewById(R.id.meme_item_id);
            hashTag = (TextView) itemView.findViewById(R.id.hashTag);
            memeImage = (ImageView) itemView.findViewById(R.id.cardImage);
            likeNum = (TextView) itemView.findViewById(R.id.likeNum);
            like = (ImageView) itemView.findViewById(R.id.like);
            shared = (Switch)itemView.findViewById(R.id.tempSwitch);
        }
    }
}
