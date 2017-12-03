package vn.loitp.restapi.livestar.corev3.api.model.v3.login.param;

import com.google.gson.annotations.SerializedName;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/29/2015
 */
public class LoginFBParam extends LoginParam {
    @SerializedName("access_token")
    private String token;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
