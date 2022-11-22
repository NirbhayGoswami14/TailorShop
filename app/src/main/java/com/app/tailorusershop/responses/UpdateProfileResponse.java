package com.app.tailorusershop.responses;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileResponse {
    @SerializedName("status")
    String status;

    @SerializedName("message")
    String message;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
