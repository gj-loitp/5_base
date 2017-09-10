package vn.puresolutions.livestar.corev3.api.model.old.def;

import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 12/21/16
 */

public enum Platform {
    @SerializedName("android")
    ANDROID("android"),
    @SerializedName("ios")
    IOS("ios");

    private String value;

    Platform(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Platform fromValue(String value) {
        for (Platform c : Platform.values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid Platform value: "
                + value);
    }
}
