package com.loitp.service.liveData

import com.loitp.service.model.ErrorResponse

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
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
