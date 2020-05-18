package com.deep.photoeditor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.deep.photoeditor.R;
import com.deep.photoeditor.adpater.PageAdapter;
import com.google.android.material.tabs.TabLayout;

public class PublicFragment extends Fragment {

    View myFragment;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;

    public PublicFragment(){

    }

    public static PublicFragment getInstance() {return new PublicFragment(); };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_public, container, false);
        myFragment = inflater.inflate(R.layout.fragment_home, container, false);

        //fragment cardview
        tabLayout = myFragment.findViewById(R.id.mainTablayout);
        viewPager = myFragment.findViewById(R.id.mainViewPager);

        return myFragment;
    }
    //call onActivity Create method
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        pagerAdapter = new PageAdapter(getActivity().getSupportFragmentManager());

        //Add Fragment here
        pagerAdapter.AddFragment(new PublicMMFragment(),"梗圖");
        pagerAdapter.AddFragment(new maintab2(),"長輩圖");
        pagerAdapter.AddFragment(new maintab2(),"動圖");

        viewPager.setAdapter(pagerAdapter);

    }
}
