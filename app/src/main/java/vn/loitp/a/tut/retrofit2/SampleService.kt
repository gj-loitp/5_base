package vn.loitp.a.tut.retrofit2

import io.reactivex.Observable
import retrofit2.http.GET

interface SampleService {
    @GET("prices?key=9c606ce987dbd04542dcfa4d87bb14ad")
    fun getData(): Observable<List<RetroCrypto>>
}
