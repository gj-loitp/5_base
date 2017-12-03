
package vn.loitp.restapi.livestar.corev3.api.model.v3.roomfindbyID;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.loitp.restapi.livestar.corev3.api.model.v3.BaseModel;
import vn.loitp.restapi.livestar.corev3.api.model.v3.categoryget.Category;
import vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbycategory.Backgrounds;
import vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbycategory.Session;
import vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbycategory.User;
import vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Banners;

public class RoomFindByID extends BaseModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("background")
    @Expose
    private Object background;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("thumb")
    @Expose
    private Object thumb;
    @SerializedName("thumbCrop")
    @Expose
    private Object thumbCrop;
    @SerializedName("thumbPoster")
    @Expose
    private Object thumbPoster;
    @SerializedName("onAir")
    @Expose
    private boolean onAir;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("receivedHeart")
    @Expose
    private int receivedHeart;
    @SerializedName("follow")
    @Expose
    private int follow;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("ticketId")
    @Expose
    private Object ticketId;
    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("Category")
    @Expose
    private Category category;
    @SerializedName("Schedules")
    @Expose
    private List<Object> schedules = null;
    @SerializedName("banners")
    @Expose
    private Banners banners;
    @SerializedName("backgrounds")
    @Expose
    private Backgrounds backgrounds;
    @SerializedName("isFollow")
    @Expose
    private boolean isFollow;
    @SerializedName("Session")
    @Expose
    private Session session;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Object getBackground() {
        return background;
    }

    public void setBackground(Object background) {
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getThumb() {
        return thumb;
    }

    public void setThumb(Object thumb) {
        this.thumb = thumb;
    }

    public Object getThumbCrop() {
        return thumbCrop;
    }

    public void setThumbCrop(Object thumbCrop) {
        this.thumbCrop = thumbCrop;
    }

    public Object getThumbPoster() {
        return thumbPoster;
    }

    public void setThumbPoster(Object thumbPoster) {
        this.thumbPoster = thumbPoster;
    }

    public boolean isOnAir() {
        return onAir;
    }

    public void setOnAir(boolean onAir) {
        this.onAir = onAir;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getReceivedHeart() {
        return receivedHeart;
    }

    public void setReceivedHeart(int receivedHeart) {
        this.receivedHeart = receivedHeart;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
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

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getTicketId() {
        return ticketId;
    }

    public void setTicketId(Object ticketId) {
        this.ticketId = ticketId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Object> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Object> schedules) {
        this.schedules = schedules;
    }

    public Banners getBanners() {
        return banners;
    }

    public void setBanners(Banners banners) {
        this.banners = banners;
    }

    public Backgrounds getBackgrounds() {
        return backgrounds;
    }

    public void setBackgrounds(Backgrounds backgrounds) {
        this.backgrounds = backgrounds;
    }

    public boolean isIsFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
