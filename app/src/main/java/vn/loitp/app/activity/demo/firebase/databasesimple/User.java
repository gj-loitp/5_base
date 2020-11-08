package vn.loitp.app.activity.demo.firebase.databasesimple;

import androidx.annotation.Keep;

import com.core.base.BaseModel;

/**
 * Created by LENOVO on 6/5/2018.
 */

@Keep
//TODO convert kotlin
public class User extends BaseModel {
    private String name;
    private String avt;
    private String msg;
    private long timestamp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvt() {
        return avt;
    }

    public void setAvt(String avt) {
        this.avt = avt;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (timestamp != user.timestamp) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (avt != null ? !avt.equals(user.avt) : user.avt != null) return false;
        return msg != null ? msg.equals(user.msg) : user.msg == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (avt != null ? avt.hashCode() : 0);
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
        return result;
    }
}
