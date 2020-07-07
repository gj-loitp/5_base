package vn.loitp.app.activity.api.coroutine.repository

import retrofit2.Response
import vn.loitp.app.activity.api.coroutine.model.ApiResponse
import vn.loitp.app.activity.api.coroutine.model.ErrorJson
import vn.loitp.app.activity.api.coroutine.model.ErrorResponse
import vn.loitp.app.activity.api.coroutine.service.RequestStatus
import vn.loitp.app.app.LApplication

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */
open class BaseRepository {

    suspend fun <T : Any> makeApiCall(call: suspend () -> Response<ApiResponse<T>>): ApiResponse<T> {

        return try {
            val response = call.invoke()

            if (response.isSuccessful) {
                response.body() ?: run {
                    ApiResponse<T>(status = true, data = null)
                }
            } else {
                if (response.code() == RequestStatus.NO_AUTHENTICATION.value) {
                    ApiResponse<T>(
                            status = false,
                            errorCode = RequestStatus.NO_AUTHENTICATION.value,
                            errors = ErrorResponse(message = "error_login"),
                            data = null
                    )
                } else {
                    handleError(response)
                }
            }

        } catch (ex: Exception) {
            val error = ex.message
            ApiResponse(
                    status = false,
                    errorCode = null,
                    errors = ErrorResponse(error),
                    data = null
            )
        }
    }

    private fun <T : Any> handleError(response: Response<ApiResponse<T>>?): ApiResponse<T> {
        var errorResponse: ErrorResponse? = null
        response?.errorBody()?.let {
            try {
                // parser error body
                val jsonError = it.string()
                val errorJson = LApplication.gson.fromJson(jsonError, ErrorJson::class.java) as ErrorJson
                errorResponse = errorJson.errors?.firstOrNull()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        if (errorResponse == null) {
            when {
                response?.code() == RequestStatus.BAD_GATEWAY.value -> return ApiResponse(
                        status = false,
                        errors = ErrorResponse(message = "error_bad_gateway"),
                        data = null,
                        errorCode = response.code()
                )
                response?.code() == RequestStatus.INTERNAL_SERVER.value -> return ApiResponse(
                        status = false,
                        errors = ErrorResponse(message = "error_internal_server"),
                        data = null,
                        errorCode = response.code()
                )
                else -> return ApiResponse(
                        status = false,
                        errors = ErrorResponse(message = "error_internal_server"),
                        data = null
                )
            }
        }

        return ApiResponse(
                status = false,
                errors = ErrorResponse(message = errorResponse?.message),
                data = null,
                errorCode = errorResponse?.code
        )
    }
}