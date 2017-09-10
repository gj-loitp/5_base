package vn.puresolutions.livestar.core.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class MoneyChanging extends BaseModel {
    @SerializedName("old_value")
    private int oldValue;
    @SerializedName("new_value")
    private int newValue;

    public int getOldValue() {
        return oldValue;
    }

    public void setOldValue(int oldValue) {
        this.oldValue = oldValue;
    }

    public int getNewValue() {
        return newValue;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }
}
