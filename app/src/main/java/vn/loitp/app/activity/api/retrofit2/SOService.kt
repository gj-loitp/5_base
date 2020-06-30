package vn.loitp.app.activity.api.retrofit2

import retrofit2.Call
import retrofit2.http.GET

interface SOService {
    @get:GET("/answers?order=desc&sort=activity&site=stackoverflow")
    val answers: Call<SOAnswersResponse>
}
