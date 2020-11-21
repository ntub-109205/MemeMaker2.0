package com.deep.photoeditor.adpater;

import android.app.Dialog;
import android.content.Context;
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
import com.felipecsl.gifimageview.library.GifImageView;
import com.wx.goodview.GoodView;

import java.util.List;

//import me.drakeet.materialdialog.MaterialDialog;


public class RecyclerViewAdapter__gifShowDialog extends RecyclerView.Adapter<RecyclerViewAdapter__gifShowDialog.MyViewHolder> {
    Context mContext;
    List<PublicMeme> mData;
    Dialog mDialog;
    private MyViewHolder mViewHolder;;
    private static final String TAG = "RecyclerViewAdapter__gif";

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
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodView mGoodView;
                mGoodView = new GoodView(mContext);
                Log.d(TAG, "onClick: clicked on: " + mData.get(position));
                ((ImageView) view).setImageResource(R.drawable.like_checked);
                mGoodView. setTextInfo("+1", Color.parseColor("#f66467"), 12);
                mGoodView.show(view);
            }
        });


        holder.item_meme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mData.get(position));
//                Toast.makeText(mContext, mData.get(position).getHashTag(), Toast.LENGTH_SHORT).show();

                GifImageView dialogImage = (GifImageView) mDialog.findViewById(R.id.gif_view);
                TextView dialogTag = (TextView) mDialog.findViewById(R.id.gif_tag);
                ImageView diaglogClose = (ImageView) mDialog.findViewById(R.id.dialog_close);
                //show GIF by using Glide
                dialogTag.setText(mData.get(position).getHashTag());
                diaglogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });
                Glide.with(mContext).load(mData.get(position).getMemeImage()).into(dialogImage);
                mDialog.show();

//                Intent intent = new Intent(mContext, PublicMemeInfoActivity.class);
//                intent.putExtra("meme_url", mData.get(position).getMemeImage());
//                intent.putExtra("hashTag", mData.get(position).getHashTag());
//                intent.putExtra("user_name", mData.get(position).getUserName());
//                intent.putExtra("like_sum", mData.get(position).getLikeSum());
//                mContext.startActivity(intent);
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
