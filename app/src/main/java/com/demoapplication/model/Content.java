package com.demoapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Content implements Serializable {
    @SerializedName("view_count")
    @Expose
    private Integer viewCount;

    @SerializedName("author_name")
    @Expose
    private String authorName;
    @SerializedName("promo_msg")
    @Expose
    private String promoMsg;
    @SerializedName("like_count")
    @Expose
    private Integer likeCount;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("read_count")
    @Expose
    private Integer readCount;

    @JsonAdapter(GuidAdapterFactory.class)
    @SerializedName("cover")
    @Expose
    private Cover cover;

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }



    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPromoMsg() {
        return promoMsg;
    }

    public void setPromoMsg(String promoMsg) {
        this.promoMsg = promoMsg;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

}
