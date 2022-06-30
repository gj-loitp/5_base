package vn.loitp.app.activity.api.retrofit2

class ApiUtils {
    companion object {
        const val baseURL = "https://api.stackexchange.com/2.2/"

        @JvmStatic
        val sOService: SOService
            get() = RetrofitClient.getClient(baseURL).create(SOService::class.java)
    }
}
