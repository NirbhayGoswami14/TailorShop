package com.app.tailorusershop.responses;

import com.google.gson.annotations.SerializedName;

public class OtpResponse {
    @SerializedName("status")
    String status;

    @SerializedName("message")
    String message;

    @SerializedName("data")
    Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

   public class Data
    {
        @SerializedName("otp")
        String otp;

        public String getOtp() {
            return otp;
        }
    }
}
