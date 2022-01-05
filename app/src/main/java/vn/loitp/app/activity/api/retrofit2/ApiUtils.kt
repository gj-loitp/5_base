package vn.loitp.app.activity.api.retrofit2

class ApiUtils {
    companion object {
        const val BASE_URL = "https://api.stackexchange.com/2.2/"

        @JvmStatic
        val sOService: SOService
            get() = RetrofitClient.getClient(BASE_URL).create(SOService::class.java)
    }
}
