package com.app.tailorusershop.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetCategoriesResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("catData")
    @Expose
    private List<CategoryData> catData = null;

    public String getStatus() {
        return status;
    }

    public List<CategoryData> getCatData() {
        return catData;
    }

    public class CategoryData
    {
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("gender")
        @Expose
        private String gender;

        public String getName() {
            return name;
        }

        public String getImage() {
            return image;
        }

        public String getGender() {
            return gender;
        }
    }
}
