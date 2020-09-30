package com.core.helper.girl.viewmodel

import com.core.base.BaseApplication
import com.core.base.BaseViewModel
import com.core.helper.girl.db.GirlDatabase
import com.core.helper.girl.model.GirlPage
import com.core.helper.girl.model.GirlPageDetail
import com.core.helper.girl.service.GirlApiClient
import com.core.helper.girl.service.GirlRepository
import com.core.utilities.LLog
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import kotlinx.coroutines.launch

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

class GirlViewModel : BaseViewModel() {
    private val logTag = "loitpp" + javaClass.simpleName
    private val repository: GirlRepository = GirlRepository(GirlApiClient.apiService)

    val pageActionLiveData: ActionLiveData<ActionData<ArrayList<GirlPage>>> = ActionLiveData()
    val pageDetailActionLiveData: ActionLiveData<ActionData<ArrayList<GirlPageDetail>>> = ActionLiveData()
    val likeGirlPageActionLiveData: ActionLiveData<ActionData<GirlPage>> = ActionLiveData()

    fun getPage(pageIndex: Int, keyWord: String?, isSwipeToRefresh: Boolean) {
        pageActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {
//            LLog.d(logTag, ">>>getPage pageIndex $pageIndex, keyWord: $keyWord, isSwipeToRefresh: $isSwipeToRefresh")
            val response = repository.getPage(
                    pageIndex = pageIndex,
                    keyWord = keyWord
            )
            LLog.d(logTag, "<<<getPage " + BaseApplication.gson.toJson(response))
            if (response.items == null) {
                pageActionLiveData.postAction(
                        getErrorRequestGirl(response)
                )
            } else {
                pageActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = response.items,
                                total = response.total,
                                totalPages = response.totalPages,
                                page = response.page,
                                isSwipeToRefresh = isSwipeToRefresh
                        )
                )
            }
        }

    }

    fun getDetail(id: String?) {
        pageDetailActionLiveData.set(ActionData(isDoing = true))
        ioScope.launch {
//            LLog.d(logTag, ">>>getDetail id $id")
            val response = repository.getPageDetail(
                    id = id
            )
            if (response.items == null) {
                pageDetailActionLiveData.postAction(
                        getErrorRequestGirl(response)
                )
            } else {
                pageDetailActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = response.items,
                                total = response.total,
                                totalPages = response.totalPages,
                                page = response.page
                        )
                )
            }
        }

    }

    fun likeGirlPage(girlPage: GirlPage) {
        LLog.d(logTag, ">>>likeGirlPage " + BaseApplication.gson.toJson(girlPage))
        likeGirlPageActionLiveData.set(ActionData(isDoing = true))
        ioScope.launch {
            val id = GirlDatabase.instance?.girlPageDao()?.insert(girlPage)
            LLog.d(logTag, "<<<likeGirlPage id $id")
            likeGirlPageActionLiveData.post(ActionData(isDoing = false, data = girlPage, isSuccess = true))

            val listGirlPageFavorites = GirlDatabase.instance?.girlPageDao()?.getListGirlPage()
            LLog.d(logTag, "listGirlPageFavorites " + BaseApplication.gson.toJson(listGirlPageFavorites))
        }
    }
}
