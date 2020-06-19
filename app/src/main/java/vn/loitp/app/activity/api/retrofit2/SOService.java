package vn.loitp.app.activity.api.retrofit2;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SOService {

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<SOAnswersResponse> getAnswers();

    //@GET("/answers?order=desc&sort=activity&site=stackoverflow")
    //Call<SOAnswersResponse> getAnswers(@Query("tagged") String tags);
}