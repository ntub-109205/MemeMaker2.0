package com.deep.photoeditor.gifmake;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.deep.photoeditor.R;
import com.deep.photoeditor.activity.GifShareActivity;
import com.deep.photoeditor.activity.MainActivity;
import com.deep.photoeditor.activity.PhotogifPublicsetting;
import com.deep.photoeditor.api;
import com.deep.photoeditor.base.GifBaseActivity;
import com.deep.photoeditor.variable;
import com.hootsuite.nachos.ChipConfiguration;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.ChipSpan;
import com.hootsuite.nachos.chip.ChipSpanChipCreator;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.hootsuite.nachos.tokenizer.SpanChipTokenizer;
import com.joyrun.gifcreator.FFmpegExecutor;
import com.joyrun.gifcreator.FFmpegUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import come.haolin_android.mvp.baselibrary.imagepicker.GlideImageLoader;
import come.haolin_android.mvp.baselibrary.imagepicker.ImagePicker;
import come.haolin_android.mvp.baselibrary.view.ToolAlertDialog;
import ru.alexbykov.nopermission.PermissionHelper;

public class VideoToGifActivity extends GifBaseActivity implements View.OnClickListener{
    private static api callApi = new api();
    private PermissionHelper permissionHelper;
    private ToolAlertDialog toolAlertDialog;
    private static com.deep.photoeditor.variable variable = new variable();
    private static final int NOT_NOTICE = 2;//如果勾选了不再询问
    private ImageView imageView;
    private File outGifDir;//gif输出文件夹
    private TextView tv_dirGif;
    public String tag = "";
    Switch switchMeme;
    public String gifShare = "1";
   // private Button btn_chooseAudio;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_to_gif_main;
    }

    @Override
    protected void initViews() {
        //新增回到前一頁的箭頭
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createChipWithText();
        Button btnNext = findViewById(R.id.btnNext);
        TextView txtSetTag = findViewById(R.id.memeTag);

        imageView = this.findViewById(R.id.imageView);
       // tv_dirGif = this.findViewById(R.id.tv_dirGif);
        ImagePicker.getInstance().imageLoader(new GlideImageLoader());
        outGifDir = new File(Environment.getExternalStorageDirectory() + "/video_gif");
        //variable.setGifPath(outGifDir.toString());
        if (!outGifDir.exists()) {
            if (outGifDir.mkdir()) {
                outGifDir = Environment.getExternalStorageDirectory();
            }
        }
        permissionHelper = new PermissionHelper(this);
        permissionHelper.check(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .onSuccess(this::onSuccess).onDenied(this::onDenied).onNeverAskAgain(this::onNeverAskAgain).run();

        //switch
        switchMeme = findViewById(R.id.memeSwitch);

        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);

        //---switchMeme---
        switchMeme.setChecked(sharedPreferences.getBoolean("value",true));
        if (switchMeme.isChecked()) {gifShare = "1";}
        else{gifShare = "0";}
        variable.memeShareSetter(gifShare);
        switchMeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switchMeme.isChecked()) {
                    //當switch被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    switchMeme.setChecked(true);
                    gifShare = "1";
                }else{
                    //當switch沒被觸發
                    SharedPreferences.Editor editor = getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    switchMeme.setChecked(false);
                    gifShare = "0";
                }
                variable.memeShareSetter(gifShare);
            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                tag = txtSetTag.getText().toString().trim();
                Log.d("tag1", tag);
                int len = tag.length();
                int x = 0;
                ArrayList a=new ArrayList();
                a.add(tag.indexOf("#"));//*第一個出現的索引位置
                while ((Integer)a.get(x)!= -1) {
                    x+=1;
                    a.add(tag.indexOf("#", (Integer)a.get(x-1)+1));//*從這個索引往後開始第一個出現的位置
                }
                a.remove(a.size()-1);
                ArrayList list=new ArrayList();
                for(int i=0;i<a.size()-1;i++) {
                    list.add(tag.substring((Integer)a.get(i)+1,(Integer)a.get(i+1)).trim());

                }
                list.add(tag.substring((Integer)a.get(a.size()-1)+1,len).trim());
                tag = "";
                for(int i=0;i<a.size();i++) {
                    tag +="#" + list.get(i).toString().trim();
                }
                Log.d("tag1", tag);
                String share = variable.memeShareGetter();
                Log.d("share",share);
                try {
                    callApi.post("http://140.131.115.99/api/txt/store",
                            "&share="+ share +"&tags="+tag);
                    Log.d("giffff","傳字串=" + callApi.returnString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("contextTest","gif的filepath=" + variable.getGifPath());
                String file = variable.getGifPath();
                try {
//                    callApi.post("http://140.131.115.99/api/meme/store",
//                            "&image="+file);
                    callApi.multipartRequest("http://140.131.115.99/api/meme/store","str="
                            , file,"image" );
                    Log.d("giffff","傳圖=" + callApi.returnString());
//                    callApi.post("http://140.131.115.99/api/meme/store","&image="+file);
//                    Log.d("giffff","傳=" + callApi.returnString());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(VideoToGifActivity.this, GifShareActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.btn_chooseAudio) {
//            permissionHelper = new PermissionHelper(this);
//            permissionHelper.check(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//                    .onSuccess(this::onSuccess).onDenied(this::onDenied).onNeverAskAgain(this::onNeverAskAgain).run();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImagePicker.getInstance().onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOT_NOTICE) {
            permissionHelper.check(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                    .onSuccess(this::onSuccess).onDenied(this::onDenied).onNeverAskAgain(this::onNeverAskAgain).run();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void onSuccess() {
        ImagePicker.getInstance()
                .multiMode(false) //多选
                .showCamera(false)
                .crop(false)
                .selectedListener(items -> {
                    execute(items.get(0).getImageUrl());
                })
                .startImagePicker(this);
    }

    /**
     * 自己写异步asyncTask  直接调用executeFFmpegCommond
     *
     * @param imageUrl
     */
    private void execute(String imageUrl) {
        showLoadingDialog();
        GifCreateAsyncTask myAsyncTask = new GifCreateAsyncTask(this);
        myAsyncTask.execute(imageUrl,outGifDir.getAbsolutePath());

    }
    /**
     * 异步调用jni生成GIF方法
     */
    private class GifCreateAsyncTask extends AsyncTask<String, Integer, String> {

        private WeakReference<VideoToGifActivity> weakReference;

        GifCreateAsyncTask(VideoToGifActivity activity) {
            Glide.with(activity).clear(activity.imageView);
            this.weakReference = new WeakReference<>(activity);
        }


        @Override
        protected String doInBackground(String... strings) {
            String inputFilePath = strings[0];
            String outGifDir = strings[1];

            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(inputFilePath);

            int width = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            int height = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            int videoHeight;
            int videoWidth;
            String metaRotation = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
            int rotation = metaRotation == null ? 0 : Integer.parseInt(metaRotation);
            Log.d("Test", "Rotation = " + rotation);
            Log.d("長寬", "寬: "+width+"長"+height);
//            if (width > height) {
//                // 横屏视屏
//                videoWidth = 1080;
//                videoHeight = (int) ((float) videoWidth / width * height);
//                Log.d("橫長寬", "寬: "+width+"長"+height);
//            } else {
//                // 竖屏视频
//                videoHeight = 1080;
//                videoWidth = (int) ((float) videoHeight / height * width);
//                Log.d("豎長寬", "寬: "+width+"長"+height);
//            }
            if (rotation==180 || rotation == 360) {
                // 横屏视屏
                videoWidth = 1080;
                videoHeight = (int) ((float) videoWidth / width * height);
                Log.d("橫長寬", "寬: "+width+"長"+height);
            } else {
                // 竖屏视频
                int tmp;
                tmp = width;
                width = height;
                height = tmp;
                videoHeight = 1080;
                videoWidth = (int) ((float) videoHeight / height * width);
                Log.d("豎長寬", "寬: "+width+"長"+height);
            }

            String gifFile = outGifDir + File.separator + "VideoToGif" + System.currentTimeMillis() + ".gif";

            String[] command = FFmpegUtils.extractVideoFramesToGIF(
                    inputFilePath,
                    0,
                    20,
                    videoWidth,
                    videoHeight,
                    10,
                    gifFile);
            FFmpegExecutor fFmpegExecutor = new FFmpegExecutor();
            fFmpegExecutor.executeFFmpegCommond(command);
            variable.setGifPath(gifFile);
            return gifFile;
        }

        @Override
        protected void onPostExecute(String gifFile) {
            super.onPostExecute(gifFile);
            final VideoToGifActivity mainActivity = weakReference.get();
            if (mainActivity != null) {
                dismissLoadingDialog();
                //tv_dirGif.setText("转换成功，gif图片路径为："+gifFile);
                Toast.makeText(mainActivity, "轉換成功", Toast.LENGTH_SHORT).show();
                Glide.with(mainActivity).asGif().load(new File(gifFile)).into(mainActivity.imageView);
            }
        }
    }



    private void onDenied() {
        if (toolAlertDialog == null) {
            toolAlertDialog = new ToolAlertDialog(mContext);
        }
        toolAlertDialog.showAlertDialog("權限申請", getResources().getString(R.string.ensureNormalOperation)
                        + getResources().getString(R.string.app_name) + getResources().getString(R.string.pleaseAllowStoreAndCam), "去允許",
                view -> {
                    toolAlertDialog.dismissAlertDialog();
                    permissionHelper.check(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                            .onSuccess(this::onSuccess).onDenied(this::onDenied).onNeverAskAgain(this::onNeverAskAgain).run();
                }, "取消", view -> toolAlertDialog.dismissAlertDialog(), false);
    }

    private void onNeverAskAgain() {
        if (toolAlertDialog == null) {
            toolAlertDialog = new ToolAlertDialog(mContext);
        }
        toolAlertDialog.showAlertDialog(mContext.getString(R.string.permissionRequest), mContext.getString(R.string.ensureNormalOperation) + getString(R.string.app_name) + mContext.getString(R.string.pleaseAllow), mContext.getString(R.string.goToAllow), view -> {
            toolAlertDialog.dismissAlertDialog();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);//注意就是"package",不用改成自己的包名
            intent.setData(uri);
            startActivityForResult(intent, NOT_NOTICE);
        }, "取消", view -> toolAlertDialog.dismissAlertDialog(), false);
    }

    private void createChipWithText() {
        NachoTextView nachoTextView = findViewById(R.id.memeTag);
        nachoTextView.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
        nachoTextView.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);

        nachoTextView.setChipTokenizer(new SpanChipTokenizer<>(this, new ChipSpanChipCreator() {
            @Override
            public ChipSpan createChip(@NonNull Context context, @NonNull CharSequence text, Object data) {
                return new ChipSpan(context, '#' + text.toString(),null, data);
            }

            @Override
            public void configureChip(@NonNull ChipSpan chip, @NonNull ChipConfiguration chipConfiguration) {
                super.configureChip(chip, chipConfiguration);
            }
        }, ChipSpan.class));
    }

}
