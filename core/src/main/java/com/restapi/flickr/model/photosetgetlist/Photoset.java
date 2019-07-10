
package com.restapi.flickr.model.photosetgetlist;

import com.core.utilities.LImageUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photoset {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("primary")
    @Expose
    private String primary;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("farm")
    @Expose
    private double farm;
    @SerializedName("photos")
    @Expose
    private String photos;
    @SerializedName("videos")
    @Expose
    private double videos;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("description")
    @Expose
    private Description description;
    @SerializedName("needs_interstitial")
    @Expose
    private double needsInterstitial;
    @SerializedName("visibility_can_see_set")
    @Expose
    private double visibilityCanSeeSet;
    @SerializedName("count_views")
    @Expose
    private String countViews;
    @SerializedName("count_comments")
    @Expose
    private String countComments;
    @SerializedName("can_comment")
    @Expose
    private double canComment;
    @SerializedName("date_create")
    @Expose
    private long dateCreate;
    @SerializedName("date_update")
    @Expose
    private long dateUpdate;
    @SerializedName("primary_photo_extras")
    @Expose
    private PrimaryPhotoExtras primaryPhotoExtras;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrimary() {
        return primary;
    }

    public void setPrimary(String primary) {
        this.primary = primary;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public double getFarm() {
        return farm;
    }

    public void setFarm(double farm) {
        this.farm = farm;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public double getVideos() {
        return videos;
    }

    public void setVideos(double videos) {
        this.videos = videos;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public double getNeedsInterstitial() {
        return needsInterstitial;
    }

    public void setNeedsInterstitial(double needsInterstitial) {
        this.needsInterstitial = needsInterstitial;
    }

    public double getVisibilityCanSeeSet() {
        return visibilityCanSeeSet;
    }

    public void setVisibilityCanSeeSet(double visibilityCanSeeSet) {
        this.visibilityCanSeeSet = visibilityCanSeeSet;
    }

    public String getCountViews() {
        return countViews;
    }

    public void setCountViews(String countViews) {
        this.countViews = countViews;
    }

    public String getCountComments() {
        return countComments;
    }

    public void setCountComments(String countComments) {
        this.countComments = countComments;
    }

    public double getCanComment() {
        return canComment;
    }

    public void setCanComment(double canComment) {
        this.canComment = canComment;
    }

    public long getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(long dateCreate) {
        this.dateCreate = dateCreate;
    }

    public long getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(long dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public PrimaryPhotoExtras getPrimaryPhotoExtras() {
        return primaryPhotoExtras;
    }

    public void setPrimaryPhotoExtras(PrimaryPhotoExtras primaryPhotoExtras) {
        this.primaryPhotoExtras = primaryPhotoExtras;
    }

    public String getFlickrLinkM() {
        if (getPrimaryPhotoExtras().getUrlO().contains(".gif")) {
            //gif extension have no link large
            return getPrimaryPhotoExtras().getUrlO();
        } else {
            return LImageUtil.getFlickrLink640(getPrimaryPhotoExtras().getUrlM());
        }
    }

    public String getFlickrLinkO() {
        if (getPrimaryPhotoExtras().getUrlO().contains(".gif")) {
            //gif extension have no link large
            return getPrimaryPhotoExtras().getUrlO();
        } else {
            return LImageUtil.getFlickrLink1024(getPrimaryPhotoExtras().getUrlM());
        }
    }
}
