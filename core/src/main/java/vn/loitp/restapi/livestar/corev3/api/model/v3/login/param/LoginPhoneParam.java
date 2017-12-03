package vn.loitp.restapi.livestar.corev3.api.model.v3.login.param;

/**
 * File created on 8/14/2017.
 *
 * @author anhdv
 */

public class LoginPhoneParam extends LoginParam {
    private String password;

    /*public LoginPhoneParam(String phone) {
        super(phone);
    }

    public LoginPhoneParam(String phone, String password) {
        super(phone);
        this.password = password;
    }*/

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
