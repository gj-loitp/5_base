package vn.loitp.up.a.api.retrofit2

class ApiUtils {
    companion object {
        private const val baseURL = "https://api.stackexchange.com/2.2/"

        @JvmStatic
        val sOService: SOService
            get() = RetrofitClient.getClient(baseURL).create(SOService::class.java)
    }
}
