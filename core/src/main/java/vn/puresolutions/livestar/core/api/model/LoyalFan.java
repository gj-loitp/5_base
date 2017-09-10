package vn.puresolutions.livestar.core.api.model;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class LoyalFan extends BaseModel {
    private transient int index;
    private long id;
    private String name;
    private String avatar;
    private int level;
    private int quantity;
    private int total_money;
    private int count;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalMoney() {
        return total_money;
    }

    public void setTotalMoney(int money) {
        this.total_money = money;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
