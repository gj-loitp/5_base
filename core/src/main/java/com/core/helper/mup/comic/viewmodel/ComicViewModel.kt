package com.core.helper.mup.comic.viewmodel

import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.helper.mup.comic.model.Comic
import com.core.helper.mup.comic.service.BaseComicViewModel
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
class ComicViewModel : BaseComicViewModel() {
    private val repository = ComicRepository(ComicApiClient.apiService)

    val listComicActionLiveData: ActionLiveData<ActionData<List<Comic>>> = ActionLiveData()

    fun getListComic(pageIndex: Int, keyword: String?, isSwipeToRefresh: Boolean) {
        listComicActionLiveData.set(ActionData(isDoing = true))
        logD(">>>getListComic pageIndex $pageIndex, keyword $keyword, isSwipeToRefresh $isSwipeToRefresh")
        ioScope.launch {
            val response = repository.getListComic(
                    pageIndex = pageIndex,
                    keyword = keyword
            )
            logD("<<<getListComic " + BaseApplication.gson.toJson(response))
            if (response.items == null || response.isSuccess == false) {
                listComicActionLiveData.postAction(
                        getErrorRequestComic(response)
                )
            } else {
                val data = response.items
                listComicActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = data,
                                total = response.total,
                                totalPages = response.totalPages,
                                page = response.page,
                                isSwipeToRefresh = isSwipeToRefresh
                        )
                )
            }
        }

    }


}
