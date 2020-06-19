package vn.loitp.app.activity.api.coroutine.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import vn.loitp.app.activity.api.coroutine.livedata.ActionData
import vn.loitp.app.activity.api.coroutine.livedata.ActionLiveData
import vn.loitp.app.activity.api.coroutine.model.UserTest
import vn.loitp.app.activity.api.coroutine.repository.TestRepository
import vn.loitp.app.activity.api.coroutine.service.TestApiClient

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
    val userTestListLiveData: MutableLiveData<ArrayList<UserTest>?> = MutableLiveData()
    val userActionLiveData: ActionLiveData<ActionData<ArrayList<UserTest>>> = ActionLiveData()

    fun getUserTestListByPage(page: Int, isRefresh: Boolean) {
        userActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {
            val response = repository.getUserTest(page = page)
            //LLog.d(TAG, "getUserList page: $page -> " + LApplication.gson.toJson(response))
            if (response.data != null) {
                userActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                isSwipeToRefresh = isRefresh,
                                data = response.data
                        )
                )
            } else {
                userActionLiveData.postAction(getErrorRequest(response))
            }
        }

    }

    fun addUserList(userTestList: ArrayList<UserTest>, isRefresh: Boolean?) {
        //LLog.d(TAG, "addUserList size: ${userTestList.size}, isRefresh: $isRefresh")
        var currentUserTestList = userTestListLiveData.value
        if (isRefresh == true) {
            currentUserTestList?.clear()
        }
        if (currentUserTestList == null) {
            currentUserTestList = ArrayList()
        }
        currentUserTestList.addAll(userTestList)
        //LLog.d(TAG, "addUserList currentUserTestList " + LApplication.gson.toJson(currentUserTestList))
        userTestListLiveData.post(currentUserTestList)
    }
}
