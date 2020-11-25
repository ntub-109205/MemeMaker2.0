package com.deep.photoeditor.adpater;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.deep.photoeditor.PublicMeme;
import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.PublicMemeInfoActivity;
import com.deep.photoeditor.api;
import com.deep.photoeditor.variable;
import com.wx.goodview.GoodView;

import java.util.List;



public class RecyclerViewAdapter__gifShowDialog extends RecyclerView.Adapter<RecyclerViewAdapter__gifShowDialog.MyViewHolder> {
    Context mContext;
    List<PublicMeme> mData;
    Dialog mDialog;
    private MyViewHolder mViewHolder;;
    private static final String TAG = "RecyclerViewAdapter__gif";
    //api
    private static api callApi = new api();
    private static com.deep.photoeditor.variable variable = new variable();

    public RecyclerViewAdapter__gifShowDialog(Context mContext, List<PublicMeme> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_meme,parent,false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        //init Dialog
        mDialog = new Dialog(mContext);
        mDialog.setContentView(R.layout.dialog_gif);

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
        if(Integer.parseInt(String.valueOf(mData.get(position).getThumb())) == 0) {
            holder.like.setImageResource(R.drawable.like_gray);
        } else {
            holder.like.setImageResource(R.drawable.like_checked);
        }
        holder.like.setOnClickListener(new View.OnClickListener() {
            int thumb = Integer.parseInt(String.valueOf(mData.get(position).getThumb()));
            int likeSum = Integer.parseInt(String.valueOf(mData.get(position).getLikeSum()));
            public void onClick(View view) {
                GoodView mGoodView;
                mGoodView = new GoodView(mContext);
                Log.d("click like", "onClick: clicked on: " + mData.get(position));
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
                mData.get(position).setLikeSum(likeSum);
                mData.get(position).setThumb(thumb);
                Log.d("thumb", callApi.returnString());
            }
        });


        holder.item_meme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                variable.category_idSetter("3");

                Intent intent = new Intent(mContext, PublicMemeInfoActivity.class);
                intent.putExtra("temp_id", mData.get(position).getTempId());
                intent.putExtra("memeId", mData.get(position).getMemeId());
                intent.putExtra("meme_url", mData.get(position).getMemeImage());
                intent.putExtra("temp_url", mData.get(position).getTempImage());
                intent.putExtra("hashTag", mData.get(position).getHashTag());
                intent.putExtra("user_name", mData.get(position).getUserName());
                intent.putExtra("like_sum", mData.get(position).getLikeSum());
                intent.putExtra("thumb", mData.get(position).getThumb());
                mContext.startActivity(intent);
            }
        });


    }

    public void setViewHolderListener(View.OnClickListener listener) {
        if (listener != null) {
            mViewHolder.item_meme.setOnClickListener(listener);
        }
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item_meme = (RelativeLayout) itemView.findViewById(R.id.meme_item_id);
            hashTag = (TextView) itemView.findViewById(R.id.hashTag);
            memeImage = (ImageView) itemView.findViewById(R.id.cardImage);
            likeNum = (TextView) itemView.findViewById(R.id.likeNum);
            like = (ImageView) itemView.findViewById(R.id.like);
        }
    }
}
