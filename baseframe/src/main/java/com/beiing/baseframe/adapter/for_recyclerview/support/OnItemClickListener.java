package com.beiing.baseframe.adapter.for_recyclerview.support;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public interface OnItemClickListener<T> {
    void onItemClick(@NonNull ViewGroup parent, @NonNull View view, T t, int position);

    boolean onItemLongClick(@NonNull ViewGroup parent, @NonNull View view, T t, int position);
}