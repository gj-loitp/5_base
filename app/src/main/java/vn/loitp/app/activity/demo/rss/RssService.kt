package vn.loitp.app.activity.demo.rss

import com.rss.RssFeed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Service for RSS
 */

interface RssService {

    /**
     * No baseUrl defined. Each RSS Feed will be consumed by it's Url
     * @param url RSS Feed Url
     * @return Retrofit Call
     */
    @GET
    fun getRss(@Url url: String): Call<RssFeed>
}
