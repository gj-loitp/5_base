
package vn.loitp.restapi.uiza.model.v2.getdetailentity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.loitp.restapi.uiza.model.v2.listallentity.Item;

public class GetDetailEntity {

    @SerializedName("data")
    @Expose
    private List<Item> itemList = null;
    @SerializedName("version")
    @Expose
    private int version;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("type")
    @Expose
    private String type;

    public List<Item> getData() {
        return itemList;
    }

    public void setData(List<Item> itemList) {
        this.itemList = itemList;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}