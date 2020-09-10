package com.deep.photoeditor;

public class tagHotSearch {
    private String tempName;
    private String tempImage;
    private int serialImage;
    private String userName;
    private int usedSum;

    public tagHotSearch() {
    }

    public tagHotSearch(String tempName, String tempImage, int serialImage, String userName, int usedSum) {
        this.tempName = tempName;
        this.tempImage = tempImage;
        this.serialImage = serialImage;
        this.userName = userName;
        this.usedSum = usedSum;
    }

    //Getter
    public String getTempName() {
        return tempName;
    }

    public String getTempImage() {
        return tempImage;
    }

    public int getSerialImage() {
        return serialImage;
    }

    public String getUserName() {
        return userName;
    }

    public int getUsedSum() {
        return usedSum;
    }

    //Setter
    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public void setTempImage(String tempImage) {
        this.tempImage = tempImage;
    }

    public void setSerialImage(int serialImage) {
        this.serialImage = serialImage;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUsedSum(int usedSum) {
        this.usedSum = usedSum;
    }
}
