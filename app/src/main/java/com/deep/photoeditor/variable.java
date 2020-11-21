package com.deep.photoeditor;


import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashMap;

public class variable {
    private static Boolean useMyImage;
    private static Uri templateUri;
    private static Uri memeUri;
    private static String templateName;
    private static String templateShare;
    private static String memeShare;
    private static String template_id;
    private static String memePath;
    private static String tagName;
    private static String searchName;
    private static String category_id;
    private static String gifPath;
    private static byte[] gifByteArray;
    private static Bitmap templateImage;
    private static Bitmap memeImage;

    private static HashMap<Bitmap, Integer> ImageQueue = new HashMap<Bitmap, Integer>();
    private static ArrayList<Bitmap> templatesBitmap = new ArrayList<Bitmap>();

    //是否使用本地圖片
    public static Boolean useMyImageGetter(){
        return useMyImage;
    }
    public static void useMyImageSetter(Boolean useMyImage1){useMyImage = useMyImage1;}

    //模板Uri
    public static Uri templateUriGetter(){
        return templateUri;
    }
    public static void templateUriSetter(Uri templateUri1){templateUri = templateUri1;}

    //梗圖Uri
    public static Uri memeUriGetter(){
        return memeUri;
    }
    public static void memeUriSetter(Uri memeUri1){memeUri = memeUri1;}

    //模板名稱
    public static String templateNameGetter(){
        return templateName;
    }
    public static void templateNameSetter(String templateName1){templateName = templateName1;}

    //模板公開
    public static String templateShareGetter(){
        return templateShare;
    }
    public static void templateShareSetter(String templateShare1){templateShare = templateShare1;}

    //梗圖公開
    public static String memeShareGetter(){
        return memeShare;
    }
    public static void memeShareSetter(String memeShare1){memeShare = memeShare1;}

    //模板ID
    public static String templateIDGetter(){
        return template_id;
    }
    public static void templateIDSetter(String template_id1){template_id = template_id1;}

    //模板圖片
    public static Bitmap templateImageGetter(){
        return templateImage;
    }
    public static void templateImageSetter(Bitmap templateImage1){templateImage = templateImage1;}

    //梗圖圖片
    public static Bitmap memeImageGetter(){
        return memeImage;
    }
    public static void memeImageSetter(Bitmap memeImage1){memeImage = memeImage1;}

    //梗圖path
    public static String memePathGetter(){
        return memePath;
    }
    public static void memePathSetter(String memePath1){memePath = memePath1;}

    //多圖模板bitmap
    public static ArrayList<Bitmap> templatesBitmapGetter(){
        return templatesBitmap;
    }
    public static void templatesBitmapSetter(ArrayList<Bitmap> templatesBitmap1){templatesBitmap = templatesBitmap1;}

    //多圖模板bitmap順序
    public static HashMap<Bitmap, Integer> ImageQueueGetter(){
        return ImageQueue;
    }
    public static void ImageQueueSetter(HashMap<Bitmap, Integer> ImageQueue1){ImageQueue = ImageQueue1;}

    //搜尋&熱門tag名稱
    public static String tagNameGetter(){
        return tagName;
    }
    public static void tagNameSetter(String tagName1){tagName = tagName1;}

    //搜尋名稱
    public static String searchNameGetter(){return searchName;}
    public static void searchNameSetter(String searchName1){searchName = searchName1;}

    //梗圖還是長輩圖
    public static String category_idGetter(){return category_id;}
    public static void category_idSetter(String category_id1){category_id = category_id1;}

    //動圖陣列
    public byte[] getGifByteArray() {return gifByteArray;}
    public void setGifByteArray(byte[] gif) {gifByteArray = gif;}

    //gifpath
    public static String getGifPath(){return gifPath;}
    public void setGifPath(String path){gifPath = path;}

}
