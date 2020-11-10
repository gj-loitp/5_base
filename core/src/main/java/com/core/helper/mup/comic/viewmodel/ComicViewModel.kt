package com.core.helper.mup.comic.viewmodel

import androidx.lifecycle.MutableLiveData
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.helper.mup.comic.model.Category
import com.core.helper.mup.comic.model.Chap
import com.core.helper.mup.comic.model.ChapterDetail
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

@LogTag("ComicLoginViewModel")
class ComicViewModel : BaseComicViewModel() {
    private val repository = ComicRepository(ComicApiClient.apiService)

    val listComicActionLiveData: ActionLiveData<ActionData<List<Comic>>> = ActionLiveData()
    val listCategoryActionLiveData: ActionLiveData<ActionData<List<Category>>> = ActionLiveData()
    val listChapActionLiveData: ActionLiveData<ActionData<List<Chap>>> = ActionLiveData()
    val chapterDetailActionLiveData: ActionLiveData<ActionData<ChapterDetail>> = ActionLiveData()

    val categorySelected = MutableLiveData<Category>()

    fun getListComic(pageIndex: Int, keyword: String?, isSwipeToRefresh: Boolean) {
        listComicActionLiveData.set(ActionData(isDoing = true))
        logD(">>>getListComic pageIndex $pageIndex, keyword $keyword, isSwipeToRefresh $isSwipeToRefresh")
        ioScope.launch {
            val response = repository.getListComic(
                    pageIndex = pageIndex,
                    keyword = keyword
            )
//            logD("<<<getListComic " + BaseApplication.gson.toJson(response))
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

    fun getListComicByCategory(categoryId: String?, pageIndex: Int, keyword: String?, isSwipeToRefresh: Boolean) {
        listComicActionLiveData.set(ActionData(isDoing = true))
//        logD(">>>getListComicByCategory categoryId $categoryId, pageIndex $pageIndex, keyword $keyword, isSwipeToRefresh $isSwipeToRefresh")
        ioScope.launch {
            val response = repository.getListComicByCategory(
                    categoryId = categoryId,
                    pageIndex = pageIndex,
                    keyword = keyword
            )
//            logD("<<<getListComicByCategory " + BaseApplication.gson.toJson(response))
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

    fun getListCategory() {
        listCategoryActionLiveData.set(ActionData(isDoing = true))
        logD(">>>getListCategory")
        ioScope.launch {
            val response = repository.getListCategory()
            logD("<<<getListCategory " + BaseApplication.gson.toJson(response))
            if (response.items == null || response.isSuccess == false) {
                listCategoryActionLiveData.postAction(
                        getErrorRequestComic(response)
                )
            } else {
                val data = response.items
                listCategoryActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = data,
                                total = response.total,
                                totalPages = response.totalPages,
                                page = response.page
                        )
                )
            }
        }

    }

    fun postCategorySelected(category: Category) {
        categorySelected.postValue(category)
    }

    fun getChapterByComicId(comicId: String?, pageIndex: Int) {
        ioScope.launch {
            logD(">>>getChapterByComicId comicId $comicId, pageIndex: $pageIndex")
            val response = repository.getListChapByComicId(
                    comicId = comicId, pageIndex = pageIndex
            )
//            logD("<<<getChapterByComicId " + BaseApplication.gson.toJson(response))
            if (response.items == null || response.isSuccess == false) {
                listChapActionLiveData.postAction(
                        getErrorRequestComic(response)
                )
            } else {
                val data = response.items
                listChapActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = data,
                                total = response.total,
                                totalPages = response.totalPages,
                                page = response.page
                        )
                )
            }
        }
    }

    fun getChapterDetail(chapId: String?) {
        ioScope.launch {
            logD(">>>getChapterDetail chapId $chapId")
            val response = repository.getChapterDetail(
                    chapId = chapId
            )
            logD("<<<getChapterDetail " + BaseApplication.gson.toJson(response))
            if (response.items == null || response.isSuccess == false) {
                chapterDetailActionLiveData.postAction(
                        getErrorRequestComic(response)
                )
            } else {
                val data = response.items
                chapterDetailActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = data
                        )
                )
            }
        }
    }
}
