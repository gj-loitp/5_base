package vn.puresolutions.livestarv3.activity.livestream.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.puresolutions.livestar.corev3.api.model.v3.BaseModel;
import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbycategory.User;

/**
 * File created on 9/1/2017.
 *
 * @author Muammil
 */

public class ChatMessage extends BaseModel {
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("message")
    @Expose
    private String message;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
