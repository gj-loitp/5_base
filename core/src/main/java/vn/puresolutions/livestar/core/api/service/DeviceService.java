package vn.puresolutions.livestar.core.api.service;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import vn.puresolutions.livestar.core.api.model.param.DeviceParam;

/**
 * @author hoangphu
 * @since 12/27/16
 */

public interface DeviceService {
    String CONTROLLER = "device";

    @POST(CONTROLLER + "/register")
    Observable<Void> register(@Body DeviceParam param);
}
