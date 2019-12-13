package com.demoapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cover implements Serializable {

    public Cover(String thumbnail, String small, String original, String large) {
        this.thumbnail = thumbnail;
        this.small = small;
        this.original = original;
        this.large = large;
    }

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("small")
    @Expose
    private String small;
    @SerializedName("original")
    @Expose
    private String original;
    @SerializedName("large")
    @Expose
    private String large;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

}
