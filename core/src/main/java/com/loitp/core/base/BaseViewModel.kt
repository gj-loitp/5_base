package com.loitp.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loitp.annotation.LogTag
import com.loitp.core.utilities.LLog
import com.loitp.service.RequestStatus
import com.loitp.service.liveData.ActionData
import com.loitp.service.model.ApiResponse
import com.loitp.service.model.ErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
open class BaseViewModel : ViewModel() {
    private var logTag: String? = null

    init {
        logTag = javaClass.getAnnotation(LogTag::class.java)?.value
    }

    protected fun <T> LiveData<T>.post(data: T) = (this as MutableLiveData<T>).postValue(data)
    protected fun <T> LiveData<T>.set(data: T) {
        (this as MutableLiveData<T>).value = data
    }

    // coroutines
    @Suppress("unused")
    protected var viewModelJob = Job()
    @Suppress("unused")
    protected val ioContext = viewModelJob + Dispatchers.IO // background context
    @Suppress("unused")
    protected val uiContext = viewModelJob + Dispatchers.Main // ui context
    protected val ioScope = CoroutineScope(ioContext)
    @Suppress("unused")
    protected val uiScope = CoroutineScope(uiContext)

    // event
    @Suppress("unused")
    val eventLoading = MutableLiveData<Boolean>()
    @Suppress("unused")
    val eventErrorMessage = MutableLiveData<String?>()

    @Suppress("unused")
    fun showLoading(value: Boolean) {
        eventLoading.post(value)
    }

    @Suppress("unused")
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
        return when (response.errorCode) {
            RequestStatus.NO_INTERNET_CONNECTION.value -> {
                ActionData(
                    isNetworkOffline = true,
                    isDoing = false,
                    isSuccess = false,
                    message = "error_internet_connection"
                )
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
                val error = response.errors ?: ErrorResponse(message = "error_occur")

                ActionData(
                    isDoing = false,
                    isSuccess = false,
                    errorResponse = error
                )
            }
        }
    }

    protected fun logD(msg: String) {
        logTag?.let {
            LLog.d(it, msg)
        }
    }

    protected fun logE(msg: String) {
        logTag?.let {
            LLog.e(it, msg)
        }
    }
}
