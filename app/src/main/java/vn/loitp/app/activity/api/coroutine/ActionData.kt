package vn.loitp.app.activity.api.coroutine

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
data class ActionData<T>(
        var isDoing: Boolean? = null,
        var isSuccess: Boolean? = null,
        var isNetworkOffline: Boolean? = null,
        var data: T? = null,

        var message: String? = null,
        val errorResponse: ErrorResponse? = null
)