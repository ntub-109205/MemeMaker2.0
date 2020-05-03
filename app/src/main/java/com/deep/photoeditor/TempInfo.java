package com.deep.photoeditor;

public class TempInfo {
    private String tempName;
    private String tempImage;

    public TempInfo() {

    }

    public TempInfo(String tempName, String tempImage) {
        this.tempName = tempName;
        this.tempImage = tempImage;
    }

    //getter
    public String getTempName() {
        return tempName;
    }

    public String getTempImage() {
        return tempImage;
    }

    //setter
    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public void setTempImage(String tempImage) {
        this.tempImage = tempImage;
    }
}
