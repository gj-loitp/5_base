package vn.puresolutions.livestarv3.activity.userprofile.model;

/**
 * File created on 8/11/2017.
 *
 * @author anhdv
 */

public class UserFollow {
    private String avatar;
    private String name;
    private int follower;
    private boolean onAir ;

    public UserFollow() {
    }

    public UserFollow(String avatar, String name, int follower, boolean onAir) {

        this.avatar = avatar;
        this.name = name;
        this.follower = follower;
        this.onAir = onAir;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public boolean isOnAir() {
        return onAir;
    }

    public void setOnAir(boolean onAir) {
        this.onAir = onAir;
    }
}
