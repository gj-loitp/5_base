package vn.loitp.app.activity.function.downloadmanager

import com.annotation.LogTag
import com.core.base.BaseViewModel
import com.core.helper.girl.model.GirlPage
import com.core.helper.girl.service.GirlApiClient
import com.core.helper.girl.service.GirlRepository
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

@LogTag("LStoreUtilModel")
class LStoreUtilModel : BaseViewModel() {
    private val repository: GirlRepository = GirlRepository(GirlApiClient.apiService)

    val pageActionLiveData: ActionLiveData<ActionData<ArrayList<GirlPage>>> = ActionLiveData()

    fun getPage(pageIndex: Int, keyWord: String?, isSwipeToRefresh: Boolean) {
//        pageActionLiveData.set(ActionData(isDoing = true))
//
//        ioScope.launch {
//            val response = repository.getPage(
//                    pageIndex = pageIndex,
//                    keyWord = keyWord
//            )
//            logD("<<<getPage " + BaseApplication.gson.toJson(response))
//            if (response.items == null) {
//                pageActionLiveData.postAction(
//                        getErrorRequestGirl(response)
//                )
//            } else {
//                val data = response.items
//                data.forEach { girlPage ->
//                    val findGirlPage = GirlDatabase.instance?.girlPageDao()?.find(girlPage.id)
////                    logD(">>>findGirlPage " + BaseApplication.gson.toJson(findGirlPage))
//                    girlPage.isFavorites = !(findGirlPage == null || !findGirlPage.isFavorites)
//                }
//
//                pageActionLiveData.post(
//                        ActionData(
//                                isDoing = false,
//                                isSuccess = true,
//                                data = data,
//                                total = response.total,
//                                totalPages = response.totalPages,
//                                page = response.page,
//                                isSwipeToRefresh = isSwipeToRefresh
//                        )
//                )
//            }
//        }

    }
}
