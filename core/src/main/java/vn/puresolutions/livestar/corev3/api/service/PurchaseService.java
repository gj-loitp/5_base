package vn.puresolutions.livestar.corev3.api.service;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import vn.puresolutions.livestar.corev3.api.model.old.CoinPackage;
import vn.puresolutions.livestar.corev3.api.model.old.CoinTransaction;
import vn.puresolutions.livestar.corev3.api.model.old.ReceiptData;
import vn.puresolutions.livestar.corev3.api.model.old.VipPackageResponse;
import vn.puresolutions.livestar.corev3.api.model.old.param.BuyVipMBFParam;
import vn.puresolutions.livestar.corev3.api.model.old.param.BuyVipParam;

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
