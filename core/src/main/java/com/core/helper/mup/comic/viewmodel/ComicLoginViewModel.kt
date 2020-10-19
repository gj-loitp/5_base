package com.core.helper.mup.comic.viewmodel

import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseViewModel
import com.core.helper.mup.comic.service.ComicApiClient
import com.core.helper.mup.comic.service.ComicRepository
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import kotlinx.coroutines.launch

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

@LogTag("loitppComicLoginViewModel")
class ComicLoginViewModel : BaseViewModel() {
    private val repository = ComicRepository(ComicApiClient.apiService)

    val loginActionLiveData: ActionLiveData<ActionData<Any>> = ActionLiveData()

    fun login(email: String, password: String) {
        loginActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {
            val response = repository.login(
                    email = email,
                    password = password
            )
            logD("<<<login " + BaseApplication.gson.toJson(response))
//            if (response.items == null) {
//                loginActionLiveData.postAction(
//                        getErrorRequestGirl(response)
//                )
//            } else {
//                val data = response.items
//                data.forEach { girlPage ->
//                    val findGirlPage = GirlDatabase.instance?.girlPageDao()?.find(girlPage.id)
//                    girlPage.isFavorites = !(findGirlPage == null || !findGirlPage.isFavorites)
//                }
//
//                loginActionLiveData.post(
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
        }

    }


}
