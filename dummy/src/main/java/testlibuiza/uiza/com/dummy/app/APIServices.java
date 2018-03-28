package testlibuiza.uiza.com.dummy.app;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by LENOVO on 3/28/2018.
 */

public interface APIServices {
    @GET("/2.2/questions?order=desc&sort=votes&site=stackoverflow&tagged=android&filter=withbody")
    Observable<Object> test();
}
