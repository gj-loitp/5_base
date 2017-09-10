package vn.puresolutions.livestar.corev3.api.service;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import vn.puresolutions.livestar.corev3.api.model.old.param.DeviceParam;

/**
 * @author hoangphu
 * @since 12/27/16
 */

public interface DeviceService {
    String CONTROLLER = "device";

    @POST(CONTROLLER + "/register")
    Observable<Void> register(@Body DeviceParam param);
}
