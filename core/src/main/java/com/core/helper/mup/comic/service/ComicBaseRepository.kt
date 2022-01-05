package com.core.helper.mup.comic.service

import com.core.base.BaseApplication
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
open class ComicBaseRepository {

    suspend fun <T : Any> makeApiCall(call: suspend () -> Response<ComicApiResponse<T>>): ComicApiResponse<T> {

        return try {
            val response = call.invoke()

            if (response.isSuccessful) {
                response.body() ?: run {
                    ComicApiResponse(status = true, items = null)
                }
            } else {
                if (response.code() == RequestStatus.NO_AUTHENTICATION.value) {
                    ComicApiResponse(
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
            ComicApiResponse(
                status = false,
                errorCode = null,
                errors = ErrorResponse(error),
                items = null
            )
        }
    }

    private fun <T : Any> handleError(response: Response<ComicApiResponse<T>>?): ComicApiResponse<T> {
        var errorResponse: ErrorResponse? = null
        response?.errorBody()?.let {
            try {
                // parser error body
                val jsonError = it.string()
                val errorJson =
                    BaseApplication.gson.fromJson(jsonError, ErrorJson::class.java) as ErrorJson
                errorResponse = errorJson.errors?.firstOrNull()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (errorResponse == null) {
            when {
                response?.code() == RequestStatus.BAD_GATEWAY.value -> return ComicApiResponse(
                    status = false,
                    errors = ErrorResponse(message = "error_bad_gateway"),
                    items = null,
                    errorCode = response.code()
                )
                response?.code() == RequestStatus.INTERNAL_SERVER.value -> return ComicApiResponse(
                    status = false,
                    errors = ErrorResponse(message = "error_internal_server"),
                    items = null,
                    errorCode = response.code()
                )
                else -> return ComicApiResponse(
                    status = false,
                    errors = ErrorResponse(message = "error_internal_server"),
                    items = null
                )
            }
        }

        return ComicApiResponse(
            status = false,
            errors = ErrorResponse(message = errorResponse?.message),
            items = null,
            errorCode = errorResponse?.code
        )
    }
}
