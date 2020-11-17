package com.deep.photoeditor.gifmake;


import android.Manifest;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deep.photoeditor.R;
import com.deep.photoeditor.base.GifBaseActivity;
import com.joyrun.gifcreator.FFmpegExecutor;
import com.joyrun.gifcreator.FFmpegUtils;

import java.io.File;
import java.lang.ref.WeakReference;

import come.haolin_android.mvp.baselibrary.imagepicker.GlideImageLoader;
import come.haolin_android.mvp.baselibrary.imagepicker.ImagePicker;
import come.haolin_android.mvp.baselibrary.view.ToolAlertDialog;
import ru.alexbykov.nopermission.PermissionHelper;

public class FilmToGifActivity extends GifBaseActivity implements View.OnClickListener{

    private PermissionHelper permissionHelper;
    private ToolAlertDialog toolAlertDialog;
    private static final int NOT_NOTICE = 2;//如果勾选了不再询问
    private ImageView imageView;
    private File outGifDirX;//gif输出文件夹
    private File fileX;
    private TextView tv_dirGif;
   // private Button btn_chooseAudio;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_to_gif_main;
    }

    @Override
    protected void initViews() {
        setTitleBarView();
        titleBar_title_tv.setText("video转gif");
        titleBar_more_tv.setText("保存");
        titleBar_more_tv.setVisibility(View.VISIBLE);
        titleBar_more_tv.setOnClickListener(v -> startActivity(new Intent(mContext,SaveImageActivity.class)));
        //Button btn_chooseAudio = this.findViewById(R.id.btn_chooseAudio);
        //btn_chooseAudio.setOnClickListener(this);
        imageView = this.findViewById(R.id.imageView);
        //tv_dirGif = this.findViewById(R.id.tv_dirGif);
        ImagePicker.getInstance().imageLoader(new GlideImageLoader());
        String path = "MeMe Maker";
        outGifDirX = new File(Environment.getExternalStorageDirectory() , path);
        if (!outGifDirX.exists()) {
            if (outGifDirX.mkdir()) {
                outGifDirX = Environment.getExternalStorageDirectory();
            }
        }
        permissionHelper = new PermissionHelper(this);
        permissionHelper.check(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .onSuccess(this::onSuccess).onDenied(this::onDenied).onNeverAskAgain(this::onNeverAskAgain).run();
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
        myAsyncTask.execute(imageUrl,outGifDirX.getAbsolutePath());
    }
    /**
     * 异步调用jni生成GIF方法
     */
    private class GifCreateAsyncTask extends AsyncTask<String, Integer, String> {

        private WeakReference<FilmToGifActivity> weakReference;

        GifCreateAsyncTask(FilmToGifActivity activity) {
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

            //String gifFile = outGifDir + File.separator + "VideoToGif" + System.currentTimeMillis() + ".gif";
            String gifFile = outGifDir +  System.currentTimeMillis() + ".gif";
             fileX = new File(outGifDirX,System.currentTimeMillis() + ".gif");

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
            return gifFile;
        }

        @Override
        protected void onPostExecute(String gifFile) {
            super.onPostExecute(gifFile);
            final FilmToGifActivity mainActivity = weakReference.get();
            if (mainActivity != null) {
                dismissLoadingDialog();
                //tv_dirGif.setText("转换成功，gif图片路径为："+gifFile);
                Toast.makeText(mainActivity, "转换成功", Toast.LENGTH_SHORT).show();

                Glide.with(mainActivity).asGif().load(fileX).into(mainActivity.imageView);
            }
        }
    }



    private void onDenied() {
        if (toolAlertDialog == null) {
            toolAlertDialog = new ToolAlertDialog(mContext);
        }
        toolAlertDialog.showAlertDialog("权限申请", getResources().getString(R.string.ensureNormalOperation)
                        + getResources().getString(R.string.app_name) + getResources().getString(R.string.pleaseAllowStoreAndCam), "去允许",
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

}
