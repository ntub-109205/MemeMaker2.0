package com.deep.photoeditor;


import android.graphics.Bitmap;
import android.net.Uri;

import java.util.ArrayList;

public class variable {
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
    private static byte[] gifByteArray;
    private static String gifPath;
    private static ArrayList<Bitmap> Bmp = new ArrayList<Bitmap>();
    private static ArrayList<Integer> BmpCounter = new ArrayList<Integer>();
    private static ArrayList<Bitmap> templatesBitmap = new ArrayList<Bitmap>();
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

    //多圖模板bitmap順序-1
    public static ArrayList<Bitmap> BmpGetter(){
        return Bmp;
    }
    public static void BmpSetter(ArrayList<Bitmap> Bmp1){Bmp = Bmp1;}

    //多圖模板bitmap順序-2
    public static ArrayList<Integer> BmpCounterGetter(){
        return BmpCounter;
    }
    public static void BmpCounterSetter(ArrayList<Integer> BmpCounter1){BmpCounter = BmpCounter1;}

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
    public byte[] getGifByteArray() { return this.gifByteArray; }
    public void setGifByteArray(byte[] gif) { this.gifByteArray = gif; }

    //gifpath
    public static String getGifPath(){ return gifPath; }
    public void setGifPath(String path){this.gifPath = path;}

}
