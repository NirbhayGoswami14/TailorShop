package com.app.tailorusershop.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAddressResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<AddressData> data = null;


    public String getStatus() {
        return status;
    }

    public List<AddressData> getData() {
        return data;
    }

    public class AddressData
    {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("mobile_no")
        @Expose
        private String mobileNo;
        @SerializedName("address1")
        @Expose
        private String address1;
        @SerializedName("address2")
        @Expose
        private String address2;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("default_addr")
        @Expose
        private String defaultAddr;
        @SerializedName("status")
        @Expose
        private String status;
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

        public String getName() {
            return name;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public String getAddress1() {
            return address1;
        }

        public String getAddress2() {
            return address2;
        }

        public String getCity() {
            return city;
        }

        public String getPincode() {
            return pincode;
        }

        public String getState() {
            return state;
        }

        public String getDefaultAddr() {
            return defaultAddr;
        }

        public String getStatus() {
            return status;
        }

        public String getInsertDate() {
            return insertDate;
        }

        public String getUpdateDate() {
            return updateDate;
        }
    }
}
