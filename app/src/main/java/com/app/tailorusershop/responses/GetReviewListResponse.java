package com.app.tailorusershop.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GetReviewListResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<ReviewData> data =new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ReviewData> getData() {
        return data;
    }

    public void setData(List<ReviewData> data) {
        this.data = data;
    }

    public class ReviewData {
        public String getTimelyOrderCompletionRating() {
            return timelyOrderCompletionRating;
        }

        public String getProductFittingRating() {
            return productFittingRating;
        }

        public String getOrderCreationRating() {
            return orderCreationRating;
        }

        public String getComments() {
            return comments;
        }

        @SerializedName("timely_order_completion_rating")
        @Expose
        private String timelyOrderCompletionRating;
        @SerializedName("product_fitting_rating")
        @Expose
        private String productFittingRating;
        @SerializedName("order_creation_rating")
        @Expose
        private String orderCreationRating;
        @SerializedName("comments")
        @Expose
        private String comments;
    }
}
