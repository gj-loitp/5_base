
package vn.loitp.restapi.uiza.model.v1.listallentity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("referenceId")
    @Expose
    private String referenceId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("ingestMetadataId")
    @Expose
    private String ingestMetadataId;
    @SerializedName("adminUserId")
    @Expose
    private String adminUserId;
    @SerializedName("poster")
    @Expose
    private String poster;
    @SerializedName("view")
    @Expose
    private double view;
    @SerializedName("status")
    @Expose
    private double status;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("ownerEmail")
    @Expose
    private String ownerEmail;
    @SerializedName("ownerAvatar")
    @Expose
    private String ownerAvatar;
    @SerializedName("ownerFullName")
    @Expose
    private String ownerFullName;
    @SerializedName("metadata")
    @Expose
    private List<Metadatum> metadata = null;
    @SerializedName("extendData")
    @Expose
    private ExtendData extendData;
    @SerializedName("subtitle")
    @Expose
    private List<Subtitle> subtitle = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getIngestMetadataId() {
        return ingestMetadataId;
    }

    public void setIngestMetadataId(String ingestMetadataId) {
        this.ingestMetadataId = ingestMetadataId;
    }

    public String getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(String adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public double getView() {
        return view;
    }

    public void setView(double view) {
        this.view = view;
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }

    public String getThumbnail() {
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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public void setOwnerAvatar(String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
    }

    public String getOwnerFullName() {
        return ownerFullName;
    }

    public void setOwnerFullName(String ownerFullName) {
        this.ownerFullName = ownerFullName;
    }

    public List<Metadatum> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<Metadatum> metadata) {
        this.metadata = metadata;
    }

    public ExtendData getExtendData() {
        return extendData;
    }

    public void setExtendData(ExtendData extendData) {
        this.extendData = extendData;
    }

    public List<Subtitle> getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(List<Subtitle> subtitle) {
        this.subtitle = subtitle;
    }
}
