package com.deep.photoeditor;

public class worMemTmp {
    private String temp_id;
    private String tempName;
    private String tempImage;
    private String userName;
    private int usedSum;
    private int shared;

    public worMemTmp() {

    }

    public worMemTmp(String temp_id, String tempName, String tempImage, String userName, int usedSum,int shared) {
        this.temp_id = temp_id;
        this.tempName = tempName;
        this.tempImage = tempImage;
        this.userName = userName;
        this.usedSum = usedSum;
        this.shared = shared;
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
    public int getShared() {
        return shared;
    }

    //setter
    public void setTemp_id(String temp_id) { this.temp_id = temp_id; }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public void setTempImage(String tempImage) { this.tempImage = tempImage; }

    public void setUserName(String userName) { this.userName = userName; }

    public void setUsedSum(int usedSum) { this.usedSum = usedSum; }
    public void setShared(int shared) {this.shared = shared; }
}

