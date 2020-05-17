package com.deep.photoeditor;

public class hotTemplate {
    private String tempName;
    private String tempImage;
    private String userName;
    private int usedSum;

    public hotTemplate() {

    }

    public hotTemplate(String tempName, String tempImage, String userName, int usedSum) {
        this.tempName = tempName;
        this.tempImage = tempImage;
        this.userName = userName;
        this.usedSum = usedSum;
    }

    //getter
    public String getTempName() {
        return tempName;
    }

    public String getTempImage() {
        return tempImage;
    }

    public String getUserName() {
        return userName;
    }

    public int getUsedSum() {
        return usedSum;
    }

    //setter
    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public void setTempImage(String tempImage) {
        this.tempImage = tempImage;
    }

    public void setUserName(String userName) { this.userName = userName; }

    public void setUsedSum(int usedSum) { this.usedSum = usedSum; }
}
