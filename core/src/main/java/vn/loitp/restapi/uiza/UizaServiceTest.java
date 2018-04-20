package vn.loitp.restapi.uiza;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author loitp
 */

public interface UizaServiceTest {
    @FormUrlEncoded
    @POST("/api/public/v1/auth/credentical")
    Observable<Object> auth(@Field("accessKeyId") String accessKeyId, @Field("secretKeyId") String secretKeyId);

    @POST("/api/data/v1/metadata/list")
    Observable<Object> getMetadatList();
}
