package vn.puresolutions.livestar.corev3.api.model.old;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class GiftQuantity extends BaseModel {
    private int quantity;
    private int resourceId;

    public GiftQuantity(int quantity, int resourceId) {
        this.quantity = quantity;
        this.resourceId = resourceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
