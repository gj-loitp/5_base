package com.function.epub.viewmodels

import com.annotation.LogTag
import com.core.base.BaseViewModel
import com.function.epub.CssStatus
import com.function.epub.Reader
import com.function.epub.exception.ReadingException
import com.function.epub.model.BookInfo
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import com.service.model.ErrorResponse
import kotlinx.coroutines.launch

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

@LogTag("GirlViewModel")
class EpubViewModel : BaseViewModel() {

    val loadDataActionLiveData: ActionLiveData<ActionData<Int>> = ActionLiveData()

    fun loadData(reader: Reader, bookInfo: BookInfo) {
        loadDataActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {
            var lastSavedPage = 0
            try {
                // Setting optionals once per file is enough.
                reader.setMaxContentPerSection(1250)
                //reader.setMaxContentPerSection(1250 * 10);
                reader.setCssStatus(CssStatus.INCLUDE)
                reader.setIsIncludingTextContent(true)
                reader.setIsOmittingTitleTag(true)
                // This method must be called before readSection.
                reader.setFullContent(bookInfo.filePath)
                // int lastSavedPage = reader.setFullContentWithProgress(filePath);
                if (reader.isSavedProgressFound) {
                    lastSavedPage = reader.loadProgress()
                }
                loadDataActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = lastSavedPage
                        )
                )
            } catch (e: ReadingException) {
                e.printStackTrace()
                loadDataActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = false,
                                errorResponse = ErrorResponse(
                                        message = e.message
                                )
                        )
                )
            }
        }

    }

}
