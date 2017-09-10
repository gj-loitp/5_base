package vn.puresolutions.livestar.core.api.model.param;

import com.google.gson.annotations.SerializedName;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/29/2015
 */
public class SignInFBParam extends SignInParam {
    @SerializedName("access_token")
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
