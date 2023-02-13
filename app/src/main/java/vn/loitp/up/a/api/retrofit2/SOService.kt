package vn.loitp.up.a.api.retrofit2

import retrofit2.Call
import retrofit2.http.GET
import vn.loitp.up.a.api.retrofit2.md.SOAnswersResponse

interface SOService {
    @get:GET("/answers?order=desc&sort=activity&site=stackoverflow")
    val answers: Call<SOAnswersResponse>
}
