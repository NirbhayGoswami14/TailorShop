package com.app.tailorusershop.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("vid")
    @Expose
    private String vid;
    @SerializedName("prodname")
    @Expose
    private String prodname;
    @SerializedName("prodstatus")
    @Expose
    private String prodstatus;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vendor_order_id")
    @Expose
    private String vendorOrderId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("delivery_date")
    @Expose
    private String deliveryDate;
    @SerializedName("trial_date")
    @Expose
    private String trialDate;
    @SerializedName("alteration_type")
    @Expose
    private String alterationType;
    @SerializedName("is_urgent")
    @Expose
    private String isUrgent;
    @SerializedName("cloth_image1")
    @Expose
    private String clothImage1;
    @SerializedName("cloth_image2")
    @Expose
    private String clothImage2;
    @SerializedName("pattern_image1")
    @Expose
    private String patternImage1;
    @SerializedName("pattern_image2")
    @Expose
    private String patternImage2;
    @SerializedName("prodMeasurements")
    @Expose
    private List<ProdMeasurement> prodMeasurements = null;
    @SerializedName("prodAttr")
    @Expose
    private List<ProdAttr> prodAttr = null;

    public String getId() {
        return id;
    }

    public String getVid() {
        return vid;
    }

    public String getProdname() {
        return prodname;
    }

    public String getProdstatus() {
        return prodstatus;
    }

    public String getStatus() {
        return status;
    }

    public String getVendorOrderId() {
        return vendorOrderId;
    }

    public String getPrice() {
        return price;
    }

    public String getNotes() {
        return notes;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public String getTrialDate() {
        return trialDate;
    }

    public String getAlterationType() {
        return alterationType;
    }

    public String getIsUrgent() {
        return isUrgent;
    }

    public String getClothImage1() {
        return clothImage1;
    }

    public String getClothImage2() {
        return clothImage2;
    }

    public String getPatternImage1() {
        return patternImage1;
    }

    public String getPatternImage2() {
        return patternImage2;
    }

    public List<ProdMeasurement> getProdMeasurements() {
        return prodMeasurements;
    }

    public List<ProdAttr> getProdAttr() {
        return prodAttr;
    }
}
