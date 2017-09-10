package vn.puresolutions.livestar.corev3.api.model.old.param;


import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class BuyVipParam {
    @SerializedName("vip_package_id")
    private long packageId;

    public BuyVipParam(long packageId) {
        this.packageId = packageId;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }
}
