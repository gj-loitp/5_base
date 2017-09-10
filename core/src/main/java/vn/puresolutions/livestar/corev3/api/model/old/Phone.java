package vn.puresolutions.livestar.corev3.api.model.old;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class Phone extends BaseModel {
    private String phone;

    public Phone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
