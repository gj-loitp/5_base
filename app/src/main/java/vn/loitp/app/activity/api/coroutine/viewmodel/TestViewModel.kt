package vn.loitp.app.activity.api.coroutine.viewmodel

import android.app.Application
import com.core.utilities.LLog
import kotlinx.coroutines.launch
import vn.loitp.app.activity.api.coroutine.livedata.ActionData
import vn.loitp.app.activity.api.coroutine.livedata.ActionLiveData
import vn.loitp.app.activity.api.coroutine.model.UserTest
import vn.loitp.app.activity.api.coroutine.repository.TestRepository
import vn.loitp.app.activity.api.coroutine.service.TestApiClient
import vn.loitp.app.app.LApplication

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

class TestViewModel(application: Application) : BaseViewModel(application) {
    private val TAG = "loitpp" + javaClass.simpleName
    private val repository: TestRepository = TestRepository(TestApiClient.apiService)

    // action
    val userAction: ActionLiveData<ActionData<List<UserTest>>> = ActionLiveData()

    init {
        //getUserList()
    }

    fun getUserList() {
        userAction.set(ActionData(isDoing = true))

        ioScope.launch {
            val page = 1
            val response = repository.getUserTest(page = page)
            LLog.d(TAG, "getUserList " + LApplication.gson.toJson(response))
            if (response.data != null) {
                userAction.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = response.data
                        )
                )
            } else {
                userAction.postAction(getErrorRequest(response))
            }
        }

    }
}
