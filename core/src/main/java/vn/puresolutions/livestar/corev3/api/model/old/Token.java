package vn.puresolutions.livestar.corev3.api.model.old;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/25/2015
 */
public class Token extends BaseModel {
    private String token;
    private String phone; // use this field in case request from 3g mbf

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
