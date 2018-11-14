
package vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.loitp.core.common.Constants;
import vn.loitp.restapi.uiza.model.v3.livestreaming.retrievealiveevent.LastPullInfo;
import vn.loitp.restapi.uiza.model.v3.livestreaming.retrievealiveevent.LastPushInfo;

public class Data {

    @SerializedName("entityId")
    @Expose
    private String entityId;

    @SerializedName("entityName")
    @Expose
    private String entityName;

    @SerializedName("feedId")
    @Expose
    private String feedId;

    @SerializedName("startTime")
    @Expose
    private String startTime;

    @SerializedName("endTime")
    @Expose
    private Object endTime;

    @SerializedName("length")
    @Expose
    private Object length;

    @SerializedName("process")
    @Expose
    private String process;

    @SerializedName("deletedEntity")
    @Expose
    private Object deletedEntity;

    @SerializedName("pullInfo")
    @Expose
    private String pullInfo;

    @SerializedName("pushInfo")
    @Expose
    private String pushInfo;

    @SerializedName("mode")
    @Expose
    private String mode;

    @SerializedName("resourceMode")
    @Expose
    private String resourceMode;

    @SerializedName("encode")
    @Expose
    private long encode;

    @SerializedName("channelName")
    @Expose
    private String channelName;

    @SerializedName("lastPresetId")
    @Expose
    private Object lastPresetId;

    @SerializedName("lastFeedId")
    @Expose
    private String lastFeedId;

    @SerializedName("linkPublishSocial")
    @Expose
    private String linkPublishSocial;

    @SerializedName("linkStream")
    @Expose
    private String linkStream;

    @SerializedName("lastPullInfo")
    @Expose
    private LastPullInfo lastPullInfo;

    @SerializedName("lastPushInfo")
    @Expose
    private List<LastPushInfo> lastPushInfo = null;

    @SerializedName("lastProcess")
    @Expose
    private String lastProcess;

    @SerializedName("eventType")
    @Expose
    private String eventType;

    @SerializedName("slug")
    @Expose
    private String slug;

    @SerializedName("orderNumber")
    @Expose
    private Integer orderNumber;

    @SerializedName("icon")
    @Expose
    private String icon;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;

    @SerializedName("view")
    @Expose
    private long view;

    @SerializedName("poster")
    @Expose
    private String poster;

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("duration")
    @Expose
    private String duration;

    @SerializedName("embedMetadata")
    @Expose
    private Object embedMetadata;

    /*@SerializedName("extendMetadata")
    @Expose
    private ExtendMetadata extendMetadata;*/

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    @SerializedName("publishToCdn")
    @Expose
    private String publishToCdn;

    @SerializedName("inputType")
    @Expose
    private String inputType;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("readyToPublish")
    @Expose
    private String readyToPublish;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public long getView() {
        return view;
    }

    public void setView(long view) {
        this.view = view;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getThumbnail() {
        if (thumbnail == null || thumbnail.isEmpty()) {
            return Constants.URL_IMG_THUMBNAIL;
        }
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Object getEmbedMetadata() {
        return embedMetadata;
    }

    public void setEmbedMetadata(Object embedMetadata) {
        this.embedMetadata = embedMetadata;
    }

    /*public ExtendMetadata getExtendMetadata() {
        return extendMetadata;
    }

    public void setExtendMetadata(ExtendMetadata extendMetadata) {
        this.extendMetadata = extendMetadata;
    }*/

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getResourceMode() {
        return resourceMode;
    }

    public void setResourceMode(String resourceMode) {
        this.resourceMode = resourceMode;
    }

    public long getEncode() {
        return encode;
    }

    public void setEncode(long encode) {
        this.encode = encode;
    }

    public Object getLastPresetId() {
        return lastPresetId;
    }

    public void setLastPresetId(Object lastPresetId) {
        this.lastPresetId = lastPresetId;
    }

    public String getLastFeedId() {
        return lastFeedId;
    }

    public void setLastFeedId(String lastFeedId) {
        this.lastFeedId = lastFeedId;
    }

    public String getLinkPublishSocial() {
        return linkPublishSocial;
    }

    public void setLinkPublishSocial(String linkPublishSocial) {
        this.linkPublishSocial = linkPublishSocial;
    }

    public LastPullInfo getLastPullInfo() {
        return lastPullInfo;
    }

    public void setLastPullInfo(LastPullInfo lastPullInfo) {
        this.lastPullInfo = lastPullInfo;
    }

    public List<LastPushInfo> getLastPushInfo() {
        return lastPushInfo;
    }

    public void setLastPushInfo(List<LastPushInfo> lastPushInfo) {
        this.lastPushInfo = lastPushInfo;
    }

    public String getLastProcess() {
        return lastProcess;
    }

    public void setLastProcess(String lastProcess) {
        this.lastProcess = lastProcess;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getLinkStream() {
        return linkStream;
    }

    public void setLinkStream(String linkStream) {
        this.linkStream = linkStream;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public Object getLength() {
        return length;
    }

    public void setLength(Object length) {
        this.length = length;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Object getDeletedEntity() {
        return deletedEntity;
    }

    public void setDeletedEntity(Object deletedEntity) {
        this.deletedEntity = deletedEntity;
    }

    public String getPullInfo() {
        return pullInfo;
    }

    public void setPullInfo(String pullInfo) {
        this.pullInfo = pullInfo;
    }

    public String getPushInfo() {
        return pushInfo;
    }

    public void setPushInfo(String pushInfo) {
        this.pushInfo = pushInfo;
    }

    public String getPublishToCdn() {
        return publishToCdn;
    }

    public void setPublishToCdn(String publishToCdn) {
        this.publishToCdn = publishToCdn;
    }
}
