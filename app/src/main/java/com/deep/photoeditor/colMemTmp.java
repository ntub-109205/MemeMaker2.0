package com.deep.photoeditor;

public class colMemTmp {
    private String tempName;
    private int tempImage;

    public colMemTmp() {

    }

    public colMemTmp(String tempName, int tempImage) {
        this.tempName = tempName;
        this.tempImage = tempImage;
    }

    //getter
    public String getTempName() {
        return tempName;
    }

    public int getTempImage() {
        return tempImage;
    }

    //setter
    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public void setTempImage(int tempImage) {
        this.tempImage = tempImage;
    }
}
