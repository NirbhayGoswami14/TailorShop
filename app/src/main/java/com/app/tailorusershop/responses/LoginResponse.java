package com.app.tailorusershop.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {



    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private VerifyOtpResponse.VerifyData data;
    @SerializedName("message")
    @Expose
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public VerifyOtpResponse.VerifyData getData() {
        return data;
    }

    public void setData(VerifyOtpResponse.VerifyData data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public class LoginData {

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

    /*@SerializedName("status")
    @Expose
    public String status;

    @SerializedName("message")
    public String message;

    public String getMessage() {
        return message;
    }
    @SerializedName("data")
    @Expose
    public List<LoginData> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<LoginData> getData() {
        return data;
    }

    public void setData(List<LoginData> data) {
        this.data = data;
    }


    public class LoginData {
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

        public void setId(String id) {
            this.id = id;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getFaceImage() {
            return faceImage;
        }

        public void setFaceImage(String faceImage) {
            this.faceImage = faceImage;
        }

        public String getBodyImage() {
            return bodyImage;
        }

        public void setBodyImage(String bodyImage) {
            this.bodyImage = bodyImage;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getInsertDate() {
            return insertDate;
        }

        public void setInsertDate(String insertDate) {
            this.insertDate = insertDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }
    }*/
}
