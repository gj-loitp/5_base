package vn.loitp.app.activity.api.coroutine

import android.app.Application
import kotlinx.coroutines.launch

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

class AuthenViewModel(application: Application) : BaseAndroidViewModel(application) {

    private val repository: ApiRepository = ApiRepository(ApiAuthenClient.apiService)

    // action
    val loginAction: ActionLiveData<ActionData<LoginResponse>> = ActionLiveData()

    private var tokenFirebase: String? = null

    init {
        //initAzure()
        //getTokenFirebase()
        //loginVinEcoFarm()
    }

    // login
    fun loginVinEcoFarm() {
        loginAction.set(ActionData(isDoing = true))

        ioScope.launch {
            val request = LoginRequest(
                    token = null,
                    firebaseRegistrationKey = tokenFirebase,
                    platform = "Android"
            )

            val response = repository.login(request)
            if (response.data != null) {
                loginAction.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = response.data
                        )
                )

            } else {
                loginAction.postAction(getErrorRequest(response))
            }
        }

    }
}