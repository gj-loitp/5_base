package vn.puresolutions.livestar.corev3.api.model.old;

import com.google.gson.annotations.SerializedName;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/23/2015
 */
public class User extends BaseModel {
    private long id;
    private String name;
    private String username;
    private String email;
    private String birthday;
    private String gender;
    private String address;
    private String phone;
    @SerializedName("is_bct")
    private boolean isBroadcaster;
    private String avatar;
    private String cover;
    private String facebook;
    private String twitter;
    private String instagram;
    private int heart;
    @SerializedName("max_heart")
    private int maxHeart;
    private int money;
    @SerializedName("user_exp")
    private long exp;
    @SerializedName("percent")
    private int levelPercent;
    @SerializedName("user_level")
    private int level;
    private String token;
    @SerializedName("is_mbf")
    private boolean isMBFUser;
    private Vip vip;

    public long getId() {
        return id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isBroadcaster() {
        return isBroadcaster;
    }

    public void setIsBroadcaster(boolean isBroadcaster) {
        this.isBroadcaster = isBroadcaster;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public int getHeart() {
        return heart;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public int getLevelPercent() {
        return levelPercent;
    }

    public void setLevelPercent(int levelPercent) {
        this.levelPercent = levelPercent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isMBFUser() {
        return isMBFUser;
    }

    public void setIsMBFUser(boolean isMBFUser) {
        this.isMBFUser = isMBFUser;
    }

    public int getMaxHeart() {
        return maxHeart;
    }

    public void setMaxHeart(int maxHeart) {
        this.maxHeart = maxHeart;
    }

    public void setBroadcaster(boolean broadcaster) {
        isBroadcaster = broadcaster;
    }

    public void setMBFUser(boolean MBFUser) {
        isMBFUser = MBFUser;
    }

    public Vip getVip() {
        return vip;
    }

    public void setVip(Vip vip) {
        this.vip = vip;
    }
}
