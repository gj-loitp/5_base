package vn.loitp.livestar.corev3.api.model.v3.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.livestar.corev3.api.model.v3.BaseModel;
import vn.loitp.livestar.corev3.api.model.v3.roomgetbycategory.User;

/**
 * File created on 8/14/2017.
 *
 * @author anhdv
 */

public class UserLogin extends BaseModel {
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("error")
    @Expose
    private Integer error;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
