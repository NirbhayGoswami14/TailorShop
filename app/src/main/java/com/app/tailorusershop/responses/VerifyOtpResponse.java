package com.app.tailorusershop.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyOtpResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private VerifyData data;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public VerifyData getData() {
        return data;
    }

    public void setData(VerifyData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class VerifyData {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("mobile_no")
        @Expose
        private String mobileNo;
        @SerializedName("otp")
        @Expose
        private String otp;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("insert_date")
        @Expose
        private String insertDate;

        public String getId() {
            return id;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public String getOtp() {
            return otp;
        }

        public String getType() {
            return type;
        }

        public String getInsertDate() {
            return insertDate;
        }
    }
}
