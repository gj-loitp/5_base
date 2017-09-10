package vn.puresolutions.livestar.corev3.api.model.old;

import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class LevelChanging extends BaseModel {
    @SerializedName("new_level")
    private int newLevel;

    public int getNewLevel() {
        return newLevel;
    }

    public void setNewLevel(int newLevel) {
        this.newLevel = newLevel;
    }
}
