package vn.puresolutions.livestar.corev3.api.model.old.param;

import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class BuyVipMBFParam {
    @SerializedName("pkg_code")
    private String packageCode;

    public BuyVipMBFParam(String code) {
        this.packageCode = code;
    }

    public String getPackageCode() {
        return packageCode;
    }

    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }
}
