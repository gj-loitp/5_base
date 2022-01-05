package com.service.livedata

import com.service.model.ErrorResponse

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
