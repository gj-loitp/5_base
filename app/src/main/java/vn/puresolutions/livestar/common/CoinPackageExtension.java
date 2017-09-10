package vn.puresolutions.livestar.common;

import vn.puresolutions.livestar.core.api.model.BaseModel;
import vn.puresolutions.livestar.core.api.model.CoinPackage;
import vn.puresolutions.livestarv3.utilities.aib.Purchase;

/**
 * @author hoangphu
 * @since 11/27/16
 */
public class CoinPackageExtension extends BaseModel {
    private CoinPackage coinPackage;
    private Purchase purchase;

    public CoinPackageExtension(CoinPackage coinPackage, Purchase purchase) {
        this.setCoinPackage(coinPackage);
        this.setPurchase(purchase);
    }

    public CoinPackage getCoinPackage() {
        return coinPackage;
    }

    public void setCoinPackage(CoinPackage coinPackage) {
        this.coinPackage = coinPackage;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
