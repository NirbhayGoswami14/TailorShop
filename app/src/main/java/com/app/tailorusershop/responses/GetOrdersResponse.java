package com.app.tailorusershop.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetOrdersResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<OrderData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderData> getData() {
        return data;
    }

    public void setData(List<OrderData> data) {
        this.data = data;
    }



   public class OrderData
   {
       @SerializedName("id")
       @Expose
       private String id;
       @SerializedName("user_id")
       @Expose
       private String userId;
       @SerializedName("vid")
       @Expose
       private String vid;
       @SerializedName("vendor_order_id")
       @Expose
       private String vendorOrderId;
       @SerializedName("name")
       @Expose
       private String name;
       @SerializedName("phone_no")
       @Expose
       private String phoneNo;
       @SerializedName("email")
       @Expose
       private String email;
       @SerializedName("address")
       @Expose
       private String address;
       @SerializedName("gender")
       @Expose
       private String gender;
       @SerializedName("status")
       @Expose
       private String status;
       @SerializedName("image")
       @Expose
       private String image;
       @SerializedName("tot_amount")
       @Expose
       private String totAmount;
       @SerializedName("discount")
       @Expose
       private String discount;
       @SerializedName("payment_method")
       @Expose
       private String paymentMethod;
       @SerializedName("delivery_date")
       @Expose
       private String deliveryDate;
       @SerializedName("invoice_no")
       @Expose
       private String invoiceNo;
       @SerializedName("invoice_url")
       @Expose
       private String invoiceUrl;
       @SerializedName("otp")
       @Expose
       private String otp;
       @SerializedName("insert_date")
       @Expose
       private String insertDate;
       @SerializedName("update_date")
       @Expose
       private String updateDate;

       public String getId() {
           return id;
       }

       public String getUserId() {
           return userId;
       }

       public String getVid() {
           return vid;
       }

       public String getVendorOrderId() {
           return vendorOrderId;
       }

       public String getName() {
           return name;
       }

       public String getPhoneNo() {
           return phoneNo;
       }

       public String getEmail() {
           return email;
       }

       public String getAddress() {
           return address;
       }

       public String getGender() {
           return gender;
       }

       public String getStatus() {
           return status;
       }

       public String getImage() {
           return image;
       }

       public String getTotAmount() {
           return totAmount;
       }

       public String getDiscount() {
           return discount;
       }

       public String getPaymentMethod() {
           return paymentMethod;
       }

       public String getDeliveryDate() {
           return deliveryDate;
       }

       public String getInvoiceNo() {
           return invoiceNo;
       }

       public String getInvoiceUrl() {
           return invoiceUrl;
       }

       public String getOtp() {
           return otp;
       }

       public String getInsertDate() {
           return insertDate;
       }

       public String getUpdateDate() {
           return updateDate;
       }
   }

}
