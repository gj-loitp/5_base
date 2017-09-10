package vn.puresolutions.livestar.core.api.model;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/23/2015
 */
public class Message extends BaseModel {
    private String message;
    private User sender;
    private Vip vip;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Vip getVip() {
        return vip;
    }

    public void setVip(Vip vip) {
        this.vip = vip;
    }
}
