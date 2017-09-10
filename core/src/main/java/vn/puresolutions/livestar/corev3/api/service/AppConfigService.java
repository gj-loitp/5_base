package vn.puresolutions.livestar.corev3.api.service;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;
import vn.puresolutions.livestar.corev3.api.model.old.AppUpdate;
import vn.puresolutions.livestar.corev3.api.model.old.param.CheckUpdateParam;

/**
 * @author hoangphu
 * @since 12/29/16
 */

public interface AppConfigService {
    String CONTROLLER = "configs";

    @POST(CONTROLLER)
    Observable<AppUpdate> checkUpdate(@Body CheckUpdateParam param);
}
