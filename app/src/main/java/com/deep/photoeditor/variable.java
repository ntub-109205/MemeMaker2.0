package com.deep.photoeditor;


import android.net.Uri;

public class variable {
    private static Uri templateUri;
    private static Uri memeUri;

    private static String templateName;
    private static String templateShare;
    private static String memeShare;

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
    public static void memeShareSetter(String memeShare1){memeShare = memeShare;}
}