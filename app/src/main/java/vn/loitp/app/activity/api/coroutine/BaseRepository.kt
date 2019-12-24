package vn.loitp.app.activity.api.coroutine

import retrofit2.Response
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
            //LLog.d(response.body())

            if (response.isSuccessful) {
                response.body()?.let {
                    it
                } ?: run {
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
            // log crash
            //Crashlytics.log(ex.toString())
            //Crashlytics.logException(ex)

            //var error = AppResource.getString(R.string.error_server)
            var error = ex.message
            if (ex.toString().contains("timeout")) {
                error = "error_timeout"
            } else if (ex.toString().contains("UnknownHostException")) {
                error = "error_internet_connection"
            }

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

                // log error
                //Crashlytics.log(jsonError)
            } catch (e: Exception) {
                e.printStackTrace()
                //Crashlytics.log(e.toString())
                //Crashlytics.logException(e)
            }
        }

        if (errorResponse == null) {

            if (response?.code() == RequestStatus.BAD_GATEWAY.value) {

                return ApiResponse(
                        status = false,
                        errors = ErrorResponse(message = "error_bad_gateway"),
                        data = null,
                        errorCode = response.code()
                )
            } else if (response?.code() == RequestStatus.INTERNAL_SERVER.value) {

                return ApiResponse(
                        status = false,
                        errors = ErrorResponse(message = "error_internal_server"),
                        data = null,
                        errorCode = response.code()
                )
            } else {

                return ApiResponse(
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