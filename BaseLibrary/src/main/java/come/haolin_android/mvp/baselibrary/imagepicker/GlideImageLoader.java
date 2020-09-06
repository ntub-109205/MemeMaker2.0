package come.haolin_android.mvp.baselibrary.imagepicker;

import android.net.Uri;
import android.widget.ImageView;

import java.io.File;

import come.haolin_android.mvp.baselibrary.glideimageview.progress.GlideApp;
import come.haolin_android.mvp.baselibrary.imagepicker.loader.ImageLoader;

/**
 * author  : Hacknife
 * e-mail  : 4884280@qq.com
 * github  : http://github.com/hacknife
 * project : MediaPicker
 */
public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayFileImage(ImageView imageView, String path) {
         GlideApp.with(imageView.getContext())                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .into(imageView);
    }

    @Override
    public void displayUserImage(ImageView imageView, String path) {
         GlideApp.with(imageView.getContext()).load(path).into(imageView);
    }


    @Override
    public void displayFileVideo(String path) {

    }

    @Override
    public Class<?> displayFullImageClass() {
        return null;
    }
}
