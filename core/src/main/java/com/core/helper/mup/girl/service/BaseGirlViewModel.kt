package com.core.helper.mup.girl.service

import com.core.base.BaseViewModel
import com.service.RequestStatus
import com.service.livedata.ActionData
import com.service.model.ErrorResponse

open class BaseGirlViewModel : BaseViewModel() {

    fun <T> getErrorRequestGirl(response: GirlApiResponse<T>): ActionData<T> {
        return when (response.errorCode) {
            RequestStatus.NO_INTERNET_CONNECTION.value -> {
                ActionData(
                        isNetworkOffline = true,
                        isDoing = false,
                        isSuccess = false,
                        message = "error_internet_connection")
            }
            RequestStatus.ERROR_CLIENT.value -> {
                ActionData(
                        message = "error_occur",
                        isDoing = false,
                        isSuccess = false
                )
            }
            RequestStatus.NO_AUTHENTICATION.value -> {
                ActionData(
                        isDoing = false,
                        isSuccess = false,
                        message = "error_login"
                )

            }
            else -> {
                val error = response.errors?.let {
                    it
                } ?: ErrorResponse(message = "error_occur")

                ActionData(
                        isDoing = false,
                        isSuccess = false,
                        errorResponse = error
                )
            }
        }
    }

}
