package com.deep.photoeditor;

public class tagHotSearch {
    private int serialImage;
    private String tempName;
    private int usedSum;

    public tagHotSearch() {
    }

    public tagHotSearch(int serialImage, String tempName, int usedSum) {
        this.serialImage = serialImage;
        this.tempName = tempName;
        this.usedSum = usedSum;
    }

    //Getter
    public int getSerialImage() { return serialImage; }

    public String getTempName() {
        return tempName;
    }

    public int getUsedSum() {
        return usedSum;
    }

    //Setter
    public void setSerialImage(int serialImage) {
        this.serialImage = serialImage;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public void setUsedSum(int usedSum) {
        this.usedSum = usedSum;
    }
}
