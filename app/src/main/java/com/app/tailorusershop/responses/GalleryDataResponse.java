package com.app.tailorusershop.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GalleryDataResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private GalleryData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GalleryData getData() {
        return data;
    }

    public class GalleryData {

        @SerializedName("Kurti")
        @Expose
        private Images kurti;
        @SerializedName("Blouse")
        @Expose
        private Images blouse;

        public Images getKurti() {
            return kurti;
        }

        public Images getBlouse() {
            return blouse;
        }


    }

   public class Images {
        @SerializedName("images")
        @Expose
        private List<String> images = null;
        @SerializedName("folder_name")
        @Expose
        private String folderName;

        public List<String> getImages() {
            return images;
        }


        public String getFolderName() {
            return folderName;
        }


    }
}





