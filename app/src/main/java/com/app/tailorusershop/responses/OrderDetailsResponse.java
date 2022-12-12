package com.app.tailorusershop.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tot_payment")
    @Expose
    private Integer totPayment;
    @SerializedName("receive_payment")
    @Expose
    private Integer receivePayment;
    @SerializedName("orders")
    @Expose
    private List<Order> orders =new ArrayList<>();
    @SerializedName("products")
    @Expose
    private List<Product> products = new ArrayList<>();


    public String getStatus() {
        return status;
    }

    public Integer getTotPayment() {
        return totPayment;
    }

    public Integer getReceivePayment() {
        return receivePayment;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Product> getProducts() {
        return products;
    }
}
