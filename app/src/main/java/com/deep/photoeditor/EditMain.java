package com.deep.photoeditor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.deep.photoeditor.adpater.PageAdapter;
import com.deep.photoeditor.fragment.edittab1;
import com.deep.photoeditor.fragment.edittab2;
import com.google.android.material.tabs.TabLayout;
import android.net.Uri;

public class EditMain extends AppCompatActivity {
    public Button btnAddMeme;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public PageAdapter pagerAdapter;
    private static variable variable = new variable();

    Uri TemplateUri;
    public void init(){
        btnAddMeme = (Button)findViewById(R.id.btnAddmeme);
        tabLayout = (TabLayout)findViewById(R.id.editTablayout);
        viewPager = (ViewPager)findViewById(R.id.editViewPager);
        pagerAdapter = new PageAdapter(getSupportFragmentManager());
        pagerAdapter.AddFragment(new edittab1(),"熱門模板");
        pagerAdapter.AddFragment(new edittab2(),"我的模板");
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        btnAddMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_main);
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            TemplateUri = data.getData();
            variable.templateUriSetter(TemplateUri);
            Intent edit = new Intent();
            edit.setClass(EditMain.this, editSetname.class);
            startActivity(edit);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
