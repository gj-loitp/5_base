package vn.loitp.app.activity.api.coroutine

import android.app.Application
import com.core.utilities.LLog
import kotlinx.coroutines.launch
import vn.loitp.app.app.LApplication

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

class AuthenViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val repository: ApiRepository = ApiRepository(ApiAuthenClient.apiService)

    // action
    val userAction: ActionLiveData<ActionData<List<UserTest>>> = ActionLiveData()

    init {
        //loginVinEcoFarm()
    }

    // login
    fun getPhotoset() {
        userAction.set(ActionData(isDoing = true))

        ioScope.launch {
            val page = 1
            val response = repository.getUserTest(page = page)
            LLog.d("loitpp", "getPhotoset " + LApplication.gson.toJson(response))
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
