package com.core.helper.mup.comic.viewmodel

import com.annotation.LogTag
import com.core.helper.mup.comic.model.Login
import com.core.helper.mup.comic.service.BaseComicViewModel
import com.core.helper.mup.comic.service.ComicApiClient
import com.core.helper.mup.comic.service.ComicRepository
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import kotlinx.coroutines.launch

@LogTag("ComicLoginViewModel")
class ComicLoginViewModel : BaseComicViewModel() {
    private val repository = ComicRepository(ComicApiClient.apiService)

    val loginActionLiveData: ActionLiveData<ActionData<Login>> = ActionLiveData()

    fun login(email: String, password: String) {
        loginActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {
            val response = repository.login(
                email = email,
                password = password
            )
//            logD("<<<login " + BaseApplication.gson.toJson(response))
            if (response.items == null || response.isSuccess == false) {
                loginActionLiveData.postAction(
                    getErrorRequestComic(response)
                )
            } else {
                val data = response.items
                loginActionLiveData.post(
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
}
