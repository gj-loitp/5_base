package vn.puresolutions.livestarv3.activity.homescreen.model.idolsearch;

/**
 * File created on 8/15/2017.
 *
 * @author anhdv
 */

public class SearchResultDummy {
    private String name;
    private String avatar;
    private boolean isOnair;
    private boolean isPrivate;

    public SearchResultDummy() {
    }

    public SearchResultDummy(String name, String avatar, boolean isOnair, boolean isPrivate) {
        this.name = name;
        this.avatar = avatar;
        this.isOnair = isOnair;
        this.isPrivate = isPrivate;
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

    public boolean isOnair() {
        return isOnair;
    }

    public void setOnair(boolean onair) {
        isOnair = onair;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
