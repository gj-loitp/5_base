package vn.puresolutions.livestar.core.api.model.def;

import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 12/21/16
 */

public enum  AppType {
    @SerializedName("client")
    CLIENT("client"),
    @SerializedName("bct")
    BCT("bct");

    private String value;

    AppType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AppType fromValue(String value) {
        for (AppType c : AppType.values()) {
            if (c.value.equals(value)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid AppType value: "
                + value);
    }
}
