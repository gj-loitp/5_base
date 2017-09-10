package vn.puresolutions.livestar.core.api.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class Broadcaster extends BaseModel {
    private long id;
    @SerializedName("broadcaster_id")
    private long broadcasterId;
    private String name;
    private String birthday;
    private String horoscope;
    private String avatar;
    private String cover;
    private long heart;
    @SerializedName("bct_exp")
    private long btcExp;
    private long exp;
    @SerializedName("user_level")
    private int userLevel;
    @SerializedName("broadcaster_level")
    private int broadcasterLevel;
    @SerializedName("facebook")
    private String facebookLink;
    @SerializedName("instagram")
    private String instagramLink;
    @SerializedName("twitter")
    private String twitterLink;
    private String status;
    private String description;
    private Room room;
    private List<Media> photos;
    private List<Media> videos;
    private List<Fan> fans;
    @SerializedName("percent")
    private int levelPercent;
    private boolean isFollow;

    private List<Schedule> schedules;

    public Broadcaster() {

    }

    public long getId() {
        return id != 0 ? id : broadcasterId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getHeart() {
        return heart;
    }

    public void setHeart(long heart) {
        this.heart = heart;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public long getBtcExp() {
        return btcExp;
    }

    public void setBtcExp(long btcExp) {
        this.btcExp = btcExp;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHoroscope() {
        return horoscope;
    }

    public void setHoroscope(String horoscope) {
        this.horoscope = horoscope;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public String getTwitterLink() {
        return twitterLink;
    }

    public void setTwitterLink(String twitterLink) {
        this.twitterLink = twitterLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Media> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Media> photos) {
        this.photos = photos;
    }

    public List<Media> getVideos() {
        return videos;
    }

    public void setVideos(List<Media> videos) {
        this.videos = videos;
    }

    public List<Fan> getFans() {
        return fans;
    }

    public void setFans(List<Fan> fans) {
        this.fans = fans;
    }

    public int getLevelPercent() {
        return levelPercent;
    }

    public void setLevelPercent(int levelPercent) {
        this.levelPercent = levelPercent;
    }

    public void setBroadcasterId(long broadcasterId) {
        this.broadcasterId = broadcasterId;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    public int getBroadcasterLevel() {
        return broadcasterLevel;
    }

    public void setBroadcasterLevel(int broadcasterLevel) {
        this.broadcasterLevel = broadcasterLevel;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setIsFollow(boolean isFollow) {
        this.isFollow = isFollow;
    }
}
