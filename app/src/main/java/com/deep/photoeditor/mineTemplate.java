package com.deep.photoeditor;

public class mineTemplate {
    private String mineTempName;
    private String mineTempImage;

    public mineTemplate(){

    }

    public mineTemplate(String mineTempName, String mineTempImage) {
        this.mineTempName = mineTempName;
        this.mineTempImage = mineTempImage;
    }

    //getter
    public String getMineTempName() {
        return mineTempName;
    }

    public String getMineTempImage() {
        return mineTempImage;
    }

    //setter
    public void setMineTempName(String mineTempName) {
        this.mineTempName = mineTempName;
    }

    public void setMineTempImage(String mineTempImage) {
        this.mineTempImage = mineTempImage;
    }
}
