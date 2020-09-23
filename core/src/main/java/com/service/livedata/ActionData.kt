package com.service.livedata

import com.service.model.ErrorResponse

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
        var isSwipeToRefresh: Boolean? = null,
        var data: T? = null,

        var message: String? = null,
        var total: Int? = null,
        var page: Int? = null,
        var totalPages: Int? = null,
        val errorResponse: ErrorResponse? = null
)
