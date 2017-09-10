package vn.puresolutions.livestar.core.api.model;

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
