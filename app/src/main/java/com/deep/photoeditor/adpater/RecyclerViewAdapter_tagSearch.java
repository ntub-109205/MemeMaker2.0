package com.deep.photoeditor.adpater;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.SearchTagInfoActivity;
import com.deep.photoeditor.activity.TemplateInfoActivity;
import com.deep.photoeditor.fragment.HomeFragment;
import com.deep.photoeditor.fragment.PublicFragment;
import com.deep.photoeditor.tagSearch;
import com.deep.photoeditor.variable;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.AlignSelf;
import com.google.android.flexbox.FlexboxLayoutManager;

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
                Toast.makeText(mContext, mData.get(position).getTag(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, SearchTagInfoActivity.class);
                mContext.startActivity(intent);
            }
        });
//        holder.tagSearch_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: clicked on: " + mData.get(position));
//                Toast.makeText(mContext, mData.get(position).getTag(), Toast.LENGTH_SHORT).show();
//
////                Intent intent = new Intent(mContext, TemplateInfoActivity.class);
////                intent.putExtra("temp_id", mData.get(position).getTempId());
////                intent.putExtra("temp_url", mData.get(position).getTempImage());
////                intent.putExtra("temp_name", mData.get(position).getTempName());
////                intent.putExtra("user_name", mData.get(position).getUserName());
////                intent.putExtra("used_sum", mData.get(position).getUsedSum());
////                mContext.startActivity(intent);
//            }
//        });
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
//            txtTag.getText().toString();
//            txtTag.setImeOptions(EditorInfo.IME_ACTION_SEND);
//
//
//            txtTag.setOnKeyListener(new View.OnKeyListener() {
//
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
//                    if (KeyEvent.KEYCODE_ENTER == keyCode && KeyEvent.ACTION_DOWN == event.getAction()) {
//                        //处理事件
//                        Log.d("tagName","測試測試");
//
//                        return true;
//                    }
//                    return false;
//                }
//            });

        }
    }
}
