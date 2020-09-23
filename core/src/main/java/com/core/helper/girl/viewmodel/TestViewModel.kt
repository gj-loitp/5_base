package com.core.helper.girl.viewmodel

import com.core.base.BaseViewModel
import com.core.helper.girl.service.TestApiClient
import com.core.helper.girl.service.TestRepository
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import com.service.model.UserTest
import kotlinx.coroutines.launch

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

class TestViewModel : BaseViewModel() {
    private val logTag = javaClass.simpleName
    private val repository: TestRepository = TestRepository(TestApiClient.apiService)

    val userActionLiveData: ActionLiveData<ActionData<ArrayList<UserTest>>> = ActionLiveData()

    fun getUserTestListByPage(page: Int, isRefresh: Boolean) {
        userActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {
            val response = repository.getUserTest(page = page)
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

}
