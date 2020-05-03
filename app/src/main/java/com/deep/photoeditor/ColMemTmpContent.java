package com.deep.photoeditor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class ColMemTmpContent extends Fragment {
    View v;
    private RecyclerView myrecyclerview;
    private List<colMemTmp> colMemTmplst;

    public ColMemTmpContent() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_col_mem_tmp_content, container, false);
        myrecyclerview = (RecyclerView) v.findViewById(R.id.colMemTmpCont_recyclerView);
        RecyclerViewAdapter_colMemTmpCont recyclerViewAdapter_colMemTmpCont = new RecyclerViewAdapter_colMemTmpCont(getContext(), colMemTmplst);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        myrecyclerview.setLayoutManager(staggeredGridLayoutManager);
        myrecyclerview.setAdapter(recyclerViewAdapter_colMemTmpCont);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        colMemTmplst = new ArrayList<>();
        colMemTmplst.add(new colMemTmp("HAHAHAHA",R.drawable.person_test_pic));
        colMemTmplst.add(new colMemTmp("ASDSF",R.drawable.person_test_pic));
        colMemTmplst.add(new colMemTmp("EEEEE",R.drawable.person_test_pic));
        colMemTmplst.add(new colMemTmp("FFFFFFF",R.drawable.elder10));
        colMemTmplst.add(new colMemTmp("YYYYY",R.drawable.elder3));
        colMemTmplst.add(new colMemTmp("HHHH",R.drawable.elder4));
        colMemTmplst.add(new colMemTmp("書與EE玫瑰",R.drawable.elder5));
        colMemTmplst.add(new colMemTmp("AWWER",R.drawable.elder6));
        colMemTmplst.add(new colMemTmp("BFFB",R.drawable.elder7));
        colMemTmplst.add(new colMemTmp("WWWWWW",R.drawable.elder8));

    }

}
