package vn.puresolutions.livestar.core.api.service;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import vn.puresolutions.livestar.core.api.model.CoinPackage;
import vn.puresolutions.livestar.core.api.model.CoinTransaction;
import vn.puresolutions.livestar.core.api.model.ReceiptData;
import vn.puresolutions.livestar.core.api.model.VipPackageResponse;
import vn.puresolutions.livestar.core.api.model.param.BuyVipMBFParam;
import vn.puresolutions.livestar.core.api.model.param.BuyVipParam;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public interface PurchaseService {
    String VIP_CONTROLLER = "vips";
    String IAP_CONTROLLER = "iap";

    @GET(VIP_CONTROLLER + "/list-vip-app-mbf")
    Observable<List<VipPackageResponse>> getVipPackage(@Query("code") int code);

    @POST(VIP_CONTROLLER + "/mobifone")
    Observable<Void> buyVipMBF(@Body BuyVipMBFParam param);

    @POST(VIP_CONTROLLER + "/buy-vip")
    Observable<Void> buyVip(@Body BuyVipParam param);

    @GET(IAP_CONTROLLER + "/coins")
    Observable<List<CoinPackage>> getCoinPackage();

    @POST(IAP_CONTROLLER + "/android")
    Observable<CoinTransaction> verifyReceipt(@Body ReceiptData receipt);
}
