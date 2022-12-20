package vn.loitp.app.activity.demo.rss

import com.loitp.rss.RssFeed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RssService {
    @GET
    fun getRss(@Url url: String): Call<RssFeed>
}
