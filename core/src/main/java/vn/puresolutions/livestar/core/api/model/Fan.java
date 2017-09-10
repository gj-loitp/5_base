package vn.puresolutions.livestar.core.api.model;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class Fan extends BaseModel {
    private String id;
    private String name;
    private String vip;
    private long heart;
    private int level;

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

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public long getHeart() {
        return heart;
    }

    public void setHeart(long heart) {
        this.heart = heart;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
