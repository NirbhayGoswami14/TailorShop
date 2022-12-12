package com.app.tailorusershop.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetProfileResponse {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("message")
    public String message;

    public String getMessage() {
        return message;
    }
    @SerializedName("data")
    @Expose
    public List<ProfileData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ProfileData> getData() {
        return data;
    }

    public class ProfileData {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("vid")
        @Expose
        public String vid;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("mobile_no")
        @Expose
        public String mobileNo;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("dob")
        @Expose
        public String dob;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("face_image")
        @Expose
        public String faceImage;
        @SerializedName("body_image")
        @Expose
        public String bodyImage;
        @SerializedName("otp")
        @Expose
        public String otp;
        @SerializedName("insert_date")
        @Expose
        public String insertDate;
        @SerializedName("update_date")
        @Expose
        public String updateDate;

        public String getId() {
            return id;
        }

        public String getVid() {
            return vid;
        }

        public String getName() {
            return name;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public String getEmail() {
            return email;
        }

        public String getAddress() {
            return address;
        }

        public String getDob() {
            return dob;
        }

        public String getGender() {
            return gender;
        }

        public String getFaceImage() {
            return faceImage;
        }

        public String getBodyImage() {
            return bodyImage;
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
