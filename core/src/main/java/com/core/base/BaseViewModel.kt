package com.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.core.utilities.LLog
import com.service.RequestStatus
import com.service.livedata.ActionData
import com.service.model.ApiResponse
import com.service.model.ErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

open class BaseViewModel : ViewModel() {
    private val logTag = javaClass.simpleName

    protected fun <T> LiveData<T>.post(data: T) = (this as MutableLiveData<T>).postValue(data)
    protected fun <T> LiveData<T>.set(data: T) {
        (this as MutableLiveData<T>).value = data
    }

    // coroutines
    protected var viewModelJob = Job()
    protected val ioContext = viewModelJob + Dispatchers.IO // background context
    protected val uiContext = viewModelJob + Dispatchers.Main // ui context
    protected val ioScope = CoroutineScope(ioContext)
    protected val uiScope = CoroutineScope(uiContext)

    // event
    val eventLoading = MutableLiveData<Boolean>()
    val eventErrorMessage = MutableLiveData<String?>()

    fun showLoading(value: Boolean) {
        eventLoading.post(value)
    }

    fun setErrorMessage(error: String?) {
        if (error?.isNotEmpty() == true) {
            eventErrorMessage.post(error)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun <T> getErrorRequest(response: ApiResponse<T>): ActionData<T> {
        val errorCode = response.errorCode
        LLog.d(logTag, "getErrorRequest errorCode $errorCode")
        return when (errorCode) {
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
                ActionData<T>(
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
