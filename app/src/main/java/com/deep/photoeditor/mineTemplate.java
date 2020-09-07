package com.deep.photoeditor;

public class mineTemplate {
    private String mineTempName;
    private String mineTempImage;
    private String userName;
    private int usedSum;

    public mineTemplate(){

    }

    public mineTemplate(String mineTempName, String mineTempImage, String userName, int usedSum) {
        this.mineTempName = mineTempName;
        this.mineTempImage = mineTempImage;
        this.userName = userName;
        this.usedSum = usedSum;
    }

    //getter
    public String getMineTempName() {
        return mineTempName;
    }

    public String getMineTempImage() {
        return mineTempImage;
    }

    public String getUserName() {
        return userName;
    }

    public int getUsedSum() {
        return usedSum;
    }

    //setter
    public void setMineTempName(String mineTempName) {
        this.mineTempName = mineTempName;
    }

    public void setMineTempImage(String mineTempImage) {
        this.mineTempImage = mineTempImage;
    }

    public void setUserName(String userName) { this.userName = userName; }

    public void setUsedSum(int usedSum) { this.usedSum = usedSum; }
}
