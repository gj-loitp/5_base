package testlibuiza.uiza.com.app;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by LENOVO on 3/28/2018.
 */

public interface APIServices {
    @GET("/2.2/questions?order=desc&sort=votes&site=stackoverflow&tagged=android&filter=withbody")
    Observable<Object> test();

    @GET("/2.2/questions/{id}/answers?order=desc&sort=votes&site=stackoverflow")
    Observable<Object> test2(@Path("id") String questionId);
}


