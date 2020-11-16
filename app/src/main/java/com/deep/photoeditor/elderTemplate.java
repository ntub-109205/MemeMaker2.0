package com.deep.photoeditor;

public class elderTemplate {
    private String temp_id;
    private String tempName;
    private String tempImage;
    private String userName;
    private int usedSum;

    public elderTemplate() {

    }

    public elderTemplate(String temp_id, String tempName, String tempImage, String userName, int usedSum) {
        this.temp_id = temp_id;
        this.tempName = tempName;
        this.tempImage = tempImage;
        this.userName = userName;
        this.usedSum = usedSum;
    }

    //getter
    public String getTemp_id() { return temp_id; }

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
    public void setTemp_id(String temp_id) { this.temp_id = temp_id; }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public void setTempImage(String tempImage) { this.tempImage = tempImage; }

    public void setUserName(String userName) { this.userName = userName; }

    public void setUsedSum(int usedSum) { this.usedSum = usedSum; }
}
