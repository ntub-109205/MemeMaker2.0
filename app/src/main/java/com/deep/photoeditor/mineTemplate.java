package com.deep.photoeditor;

public class mineTemplate {
    private String mineTempName;
    private int mineTempImage;

    public mineTemplate(){

    }

    public mineTemplate(String mineTempName, int mineTempImage) {
        this.mineTempName = mineTempName;
        this.mineTempImage = mineTempImage;
    }

    //getter
    public String getMineTempName() {
        return mineTempName;
    }

    public int getMineTempImage() {
        return mineTempImage;
    }

    //setter
    public void setMineTempName(String mineTempName) {
        this.mineTempName = mineTempName;
    }

    public void setMineTempImage(int mineTempImage) {
        this.mineTempImage = mineTempImage;
    }
}
