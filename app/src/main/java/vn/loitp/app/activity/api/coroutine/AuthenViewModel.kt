package vn.loitp.app.activity.api.coroutine

import android.app.Application
import com.core.utilities.LLog
import com.restapi.flickr.FlickrConst
import com.restapi.flickr.model.photosetgetlist.WrapperPhotosetGetlist
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
    val photosetAction: ActionLiveData<ActionData<WrapperPhotosetGetlist>> = ActionLiveData()

    init {
        //loginVinEcoFarm()
    }

    // login
    fun getPhotoset() {
        photosetAction.set(ActionData(isDoing = true))

        ioScope.launch {
            val method = FlickrConst.METHOD_PHOTOSETS_GETLIST
            val apiKey = FlickrConst.API_KEY
            val userID = FlickrConst.USER_KEY
            val page = 1
            val perPage = 500
            val primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_0
            val format = FlickrConst.FORMAT
            val noJsonCallback = FlickrConst.NO_JSON_CALLBACK
            val response = repository.photosetsGetList(
                    method = method,
                    apiKey = apiKey,
                    userId = userID,
                    page = page,
                    perPage = perPage,
                    primaryPhotoExtras = primaryPhotoExtras,
                    format = format,
                    noJsonCallback = noJsonCallback)
            LLog.d("loitpp", "getPhotoset " + LApplication.gson.toJson(response))
            if (response.data != null) {
                photosetAction.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = response.data
                        )
                )
            } else {
                photosetAction.postAction(getErrorRequest(response))
            }
        }

    }
}
