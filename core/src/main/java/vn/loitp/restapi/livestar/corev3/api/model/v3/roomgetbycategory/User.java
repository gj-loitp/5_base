
package vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbycategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.restapi.livestar.corev3.api.model.v3.BaseModel;
import vn.loitp.restapi.livestar.corev3.api.model.v3.login.AvatarsPath;
import vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;

public class User extends BaseModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("birthday")
    @Expose
    private Object birthday;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("avatar")
    @Expose
    private String avatar;
    @SerializedName("cover")
    @Expose
    private Object cover;
    @SerializedName("coverCrop")
    @Expose
    private Object coverCrop;
    @SerializedName("fbId")
    @Expose
    private Object fbId;
    @SerializedName("gpId")
    @Expose
    private Object gpId;
    @SerializedName("fbLink")
    @Expose
    private Object fbLink;
    @SerializedName("twitterLink")
    @Expose
    private Object twitterLink;
    @SerializedName("instagramLink")
    @Expose
    private Object instagramLink;
    @SerializedName("money")
    @Expose
    private int money;
    @SerializedName("exp")
    @Expose
    private int exp;
    @SerializedName("activeCode")
    @Expose
    private Object activeCode;
    @SerializedName("resetPasswordCode")
    @Expose
    private Object resetPasswordCode;
    @SerializedName("activeDate")
    @Expose
    private String activeDate;
    @SerializedName("isBroadcaster")
    @Expose
    private Object isBroadcaster;
    @SerializedName("heart")
    @Expose
    private int heart;
    @SerializedName("follow")
    @Expose
    private int follow;
    @SerializedName("isBanned")
    @Expose
    private Object isBanned;
    @SerializedName("token")
    @Expose
    private Object token;
    @SerializedName("lastLogin")
    @Expose
    private Object lastLogin;
    @SerializedName("levelID")
    @Expose
    private Object levelID;
    @SerializedName("failedAttempts")
    @Expose
    private Object failedAttempts;
    @SerializedName("failedAt")
    @Expose
    private Object failedAt;
    @SerializedName("lockedAt")
    @Expose
    private Object lockedAt;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("avatars_path")
    @Expose
    private AvatarsPath avatarsPath;
    @SerializedName("Room")
    @Expose
    private Room room;
    @SerializedName("isIdol")
    @Expose
    private int isIdol;
    @SerializedName("Level")
    @Expose
    private Level level;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
        this.birthday = birthday;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Object getCover() {
        return cover;
    }

    public void setCover(Object cover) {
        this.cover = cover;
    }

    public Object getCoverCrop() {
        return coverCrop;
    }

    public void setCoverCrop(Object coverCrop) {
        this.coverCrop = coverCrop;
    }

    public Object getFbId() {
        return fbId;
    }

    public void setFbId(Object fbId) {
        this.fbId = fbId;
    }

    public Object getGpId() {
        return gpId;
    }

    public void setGpId(Object gpId) {
        this.gpId = gpId;
    }

    public Object getFbLink() {
        return fbLink;
    }

    public void setFbLink(Object fbLink) {
        this.fbLink = fbLink;
    }

    public Object getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(Object twitterLink) {
        this.twitterLink = twitterLink;
    }

    public Object getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(Object instagramLink) {
        this.instagramLink = instagramLink;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public Object getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(Object activeCode) {
        this.activeCode = activeCode;
    }

    public String getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(String activeDate) {
        this.activeDate = activeDate;
    }

    public Object getIsBroadcaster() {
        return isBroadcaster;
    }

    public void setIsBroadcaster(Object isBroadcaster) {
        this.isBroadcaster = isBroadcaster;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getFollow() {
        return follow;
    }

    public void setFollow(int follow) {
        this.follow = follow;
    }

    public Object getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(Object isBanned) {
        this.isBanned = isBanned;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public Object getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Object lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Object getLevelID() {
        return levelID;
    }

    public void setLevelID(Object levelID) {
        this.levelID = levelID;
    }

    public Object getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(Object failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public Object getFailedAt() {
        return failedAt;
    }

    public void setFailedAt(Object failedAt) {
        this.failedAt = failedAt;
    }

    public Object getLockedAt() {
        return lockedAt;
    }

    public void setLockedAt(Object lockedAt) {
        this.lockedAt = lockedAt;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public AvatarsPath getAvatarsPath() {
        return avatarsPath;
    }

    public void setAvatarsPath(AvatarsPath avatarsPath) {
        this.avatarsPath = avatarsPath;
    }

    public Object getResetPasswordCode() {
        return resetPasswordCode;
    }

    public void setResetPasswordCode(Object resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getIsIdol() {
        return isIdol;
    }

    public void setIsIdol(int isIdol) {
        this.isIdol = isIdol;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
