package vn.loitp.app.activity.api.coroutine.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.core.utilities.LLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import vn.loitp.app.activity.api.coroutine.livedata.ActionData
import vn.loitp.app.activity.api.coroutine.model.ApiResponse
import vn.loitp.app.activity.api.coroutine.model.ErrorResponse
import vn.loitp.app.activity.api.coroutine.service.RequestStatus

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = javaClass.simpleName
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun <T> getErrorRequest(response: ApiResponse<T>): ActionData<T> {
        val errorCode = response.errorCode
        LLog.d(TAG, "getErrorRequest errorCode $errorCode")
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
