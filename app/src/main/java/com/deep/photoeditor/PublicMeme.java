package com.deep.photoeditor;

public class PublicMeme {
    private String tempId;
    private String memeId;
    private String hashTag;
    private String memeImage;
    private String tempImage;
    private String userName;
    private int likeSum; //使用者按讚數量
    private int thumb;//使用者是否按讚 1有 0沒有

    public PublicMeme() {

    }

    public PublicMeme(String tempId, String memeId, String hashTag, String tempImage, String memeImage, String userName, int likeSum, int thumb) {
        this.tempId = tempId;
        this.memeId = memeId;
        this.hashTag = hashTag;
        this.memeImage = memeImage;
        this.tempImage = tempImage;
        this.userName = userName;
        this.likeSum = likeSum;
        this.thumb = thumb;
    }

    //getter
    public String getTempId() { return tempId; }

    public String getMemeId() { return memeId; }

    public String getHashTag() {
        return hashTag;
    }

    public String getMemeImage() { return memeImage; }

    public String getTempImage() { return tempImage; }

    public String getUserName() {
        return userName;
    }

    public int getLikeSum() {
        return likeSum;
    }

    public int getThumb() { return thumb; }

    //setter
    public void setTempId(String tempId) { this.tempId = tempId; }

    public void setMemeId(String tempId) { this.memeId = memeId; }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public void setMemeImage(String memeImage) { this.memeImage = memeImage; }

    public void setTempImage(String tempImage) { this.tempImage = tempImage; }

    public void setUserName(String userName) { this.userName = userName; }

    public void setLikeSum(int likeSum) { this.likeSum = likeSum; }

    public void setThumb(int thumb) {this.thumb = thumb; }
}
