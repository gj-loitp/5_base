package vn.puresolutions.livestar.core.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class ExpChanging extends BaseModel {
    @SerializedName("new_value")
    private float newExp;

    @SerializedName("old_value")
    private float oldExp;

    private float percent;

    public float getNewExp() {
        return newExp;
    }

    public void setNewExp(float newExp) {
        this.newExp = newExp;
    }

    public float getOldExp() {
        return oldExp;
    }

    public void setOldExp(float oldExp) {
        this.oldExp = oldExp;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }
}
