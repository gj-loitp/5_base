package vn.puresolutions.livestar.corev3.api.model.old;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 12/26/2015
 */
public class GiftMessage extends Message {
    private Gift gift;
    private int quantity;
    private float total;

    public Gift getGift() {
        return gift;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
