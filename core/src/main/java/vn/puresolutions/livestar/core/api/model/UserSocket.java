package vn.puresolutions.livestar.core.api.model;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/23/2015
 */
public class UserSocket extends BaseModel {
    private long id;
    private String name;
    private String username;
    private String avatar;
    private int vip;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }
}
