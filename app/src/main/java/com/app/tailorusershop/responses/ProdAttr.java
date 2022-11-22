package com.app.tailorusershop.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProdAttr {

    @SerializedName("collar")
    @Expose
    private String collar;
    @SerializedName("fit")
    @Expose
    private String fit;
    @SerializedName("length_attr")
    @Expose
    private String lengthAttr;
    @SerializedName("neck_attr")
    @Expose
    private String neckAttr;
    @SerializedName("pocket")
    @Expose
    private String pocket;
    @SerializedName("sleeve_styling")
    @Expose
    private String sleeveStyling;
    @SerializedName("sleeves")
    @Expose
    private String sleeves;
    @SerializedName("top_shape")
    @Expose
    private String topShape;

    public String getCollar() {
        return collar;
    }

    public String getFit() {
        return fit;
    }

    public String getLengthAttr() {
        return lengthAttr;
    }

    public String getNeckAttr() {
        return neckAttr;
    }

    public String getPocket() {
        return pocket;
    }

    public String getSleeveStyling() {
        return sleeveStyling;
    }

    public String getSleeves() {
        return sleeves;
    }

    public String getTopShape() {
        return topShape;
    }
}
