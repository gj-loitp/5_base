package com.core.helper.girl.service

import com.google.gson.Gson
import com.service.RequestStatus
import com.service.model.ErrorJson
import com.service.model.ErrorResponse
import retrofit2.Response

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
open class GirlBaseRepository {

    suspend fun <T : Any> makeApiCall(call: suspend () -> Response<GirlApiResponse<T>>): GirlApiResponse<T> {

        return try {
            val response = call.invoke()

            if (response.isSuccessful) {
                response.body() ?: run {
                    GirlApiResponse<T>(status = true, items = null)
                }
            } else {
                if (response.code() == RequestStatus.NO_AUTHENTICATION.value) {
                    GirlApiResponse<T>(
                            status = false,
                            errorCode = RequestStatus.NO_AUTHENTICATION.value,
                            errors = ErrorResponse(message = "error_login"),
                            items = null
                    )
                } else {
                    handleError(response = response)
                }
            }

        } catch (ex: Exception) {
            val error = ex.message
            GirlApiResponse(
                    status = false,
                    errorCode = null,
                    errors = ErrorResponse(error),
                    items = null
            )
        }
    }

    private fun <T : Any> handleError(response: Response<GirlApiResponse<T>>?): GirlApiResponse<T> {
        var errorResponse: ErrorResponse? = null
        response?.errorBody()?.let {
            try {
                // parser error body
                val jsonError = it.string()
                val errorJson = Gson().fromJson(jsonError, ErrorJson::class.java) as ErrorJson
                errorResponse = errorJson.errors?.firstOrNull()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (errorResponse == null) {
            when {
                response?.code() == RequestStatus.BAD_GATEWAY.value -> return GirlApiResponse(
                        status = false,
                        errors = ErrorResponse(message = "error_bad_gateway"),
                        items = null,
                        errorCode = response.code()
                )
                response?.code() == RequestStatus.INTERNAL_SERVER.value -> return GirlApiResponse(
                        status = false,
                        errors = ErrorResponse(message = "error_internal_server"),
                        items = null,
                        errorCode = response.code()
                )
                else -> return GirlApiResponse(
                        status = false,
                        errors = ErrorResponse(message = "error_internal_server"),
                        items = null
                )
            }
        }

        return GirlApiResponse(
                status = false,
                errors = ErrorResponse(message = errorResponse?.message),
                items = null,
                errorCode = errorResponse?.code
        )
    }
}
