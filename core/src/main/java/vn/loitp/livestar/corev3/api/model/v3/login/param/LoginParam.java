package vn.loitp.livestar.corev3.api.model.v3.login.param;

import vn.loitp.livestar.corev3.api.model.v3.BaseModel;

/**
 * File created on 8/14/2017.
 *
 * @author anhdv
 */

public class LoginParam extends BaseModel {
    private String phone;

    /*public LoginParam(String phone) {
        this.phone = phone;
    }*/

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
