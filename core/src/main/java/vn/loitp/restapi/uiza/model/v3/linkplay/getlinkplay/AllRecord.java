
package vn.loitp.restapi.uiza.model.v3.linkplay.getlinkplay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllRecord {

    @SerializedName("processed_time")
    @Expose
    private Integer processedTime;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("source_uri")
    @Expose
    private String sourceUri;
    @SerializedName("region_type")
    @Expose
    private String regionType;
    @SerializedName("uploaded_size")
    @Expose
    private String uploadedSize;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("app_id")
    @Expose
    private String appId;
    @SerializedName("destination_uri")
    @Expose
    private String destinationUri;
    @SerializedName("entity_id")
    @Expose
    private String entityId;
    @SerializedName("task_id")
    @Expose
    private String taskId;
    @SerializedName("output_transcode_path")
    @Expose
    private String outputTranscodePath;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("progress")
    @Expose
    private Integer progress;
    @SerializedName("type_content")
    @Expose
    private String typeContent;
    @SerializedName("publish_id")
    @Expose
    private String publishId;

    public Integer getProcessedTime() {
        return processedTime;
    }

    public void setProcessedTime(Integer processedTime) {
        this.processedTime = processedTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSourceUri() {
        return sourceUri;
    }

    public void setSourceUri(String sourceUri) {
        this.sourceUri = sourceUri;
    }

    public String getRegionType() {
        return regionType;
    }

    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    public String getUploadedSize() {
        return uploadedSize;
    }

    public void setUploadedSize(String uploadedSize) {
        this.uploadedSize = uploadedSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDestinationUri() {
        return destinationUri;
    }

    public void setDestinationUri(String destinationUri) {
        this.destinationUri = destinationUri;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOutputTranscodePath() {
        return outputTranscodePath;
    }

    public void setOutputTranscodePath(String outputTranscodePath) {
        this.outputTranscodePath = outputTranscodePath;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getTypeContent() {
        return typeContent;
    }

    public void setTypeContent(String typeContent) {
        this.typeContent = typeContent;
    }

    public String getPublishId() {
        return publishId;
    }

    public void setPublishId(String publishId) {
        this.publishId = publishId;
    }

}
