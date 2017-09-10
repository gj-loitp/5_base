package vn.puresolutions.livestar.core.api.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/23/2015
 */
public class VipPackageResponse extends BaseModel {
    private VipPackageDetails details;
    @SerializedName("vip_packages")
    private List<VipPackage> packages;

    public VipPackageDetails getDetails() {
        return details;
    }

    public void setDetails(VipPackageDetails details) {
        this.details = details;
    }

    public List<VipPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<VipPackage> packages) {
        this.packages = packages;
    }
}
