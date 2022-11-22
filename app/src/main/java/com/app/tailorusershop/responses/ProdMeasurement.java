package com.app.tailorusershop.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProdMeasurement {

    @SerializedName("length")
    @Expose
    private String length;
    @SerializedName("shoulder")
    @Expose
    private String shoulder;
    @SerializedName("sleeve_length")
    @Expose
    private String sleeveLength;
    @SerializedName("chest")
    @Expose
    private String chest;
    @SerializedName("waist")
    @Expose
    private String waist;
    @SerializedName("upper_bust")
    @Expose
    private String upperBust;

    public String getLength() {
        return length;
    }

    public String getShoulder() {
        return shoulder;
    }

    public String getSleeveLength() {
        return sleeveLength;
    }

    public String getChest() {
        return chest;
    }

    public String getWaist() {
        return waist;
    }

    public String getUpperBust() {
        return upperBust;
    }
}
