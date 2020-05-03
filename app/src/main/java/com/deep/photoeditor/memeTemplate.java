package com.deep.photoeditor;

public class memeTemplate {
    private String tempName;
    private String tempImage;

    public memeTemplate() {

    }

    public memeTemplate(String tempName, String tempImage) {
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
