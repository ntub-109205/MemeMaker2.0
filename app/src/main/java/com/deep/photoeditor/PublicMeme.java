package com.deep.photoeditor;

public class PublicMeme {
    private String hashTag;
    private String memeImage;
    private String userName;
    private int likeSum;

    public PublicMeme() {

    }

    public PublicMeme(String hashTag, String memeImage, String userName, int likeSum) {
        this.hashTag = hashTag;
        this.memeImage = memeImage;
        this.userName = userName;
        this.likeSum = likeSum;
    }

    //getter
    public String getHashTag() {
        return hashTag;
    }

    public String getMemeImage() {
        return memeImage;
    }

    public String getUserName() {
        return userName;
    }

    public int getLikeSum() {
        return likeSum;
    }

    //setter
    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public void setMemeImage(String tempImage) { this.memeImage = memeImage; }

    public void setUserName(String userName) { this.userName = userName; }

    public void setLikeSum(int likeSum) { this.likeSum = likeSum; }
}
