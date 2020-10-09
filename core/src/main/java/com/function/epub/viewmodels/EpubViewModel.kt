package com.function.epub.viewmodels

import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseViewModel
import com.core.utilities.LAppResource
import com.core.utilities.LPrefUtil
import com.function.epub.CssStatus
import com.function.epub.Reader
import com.function.epub.exception.ReadingException
import com.function.epub.model.BookInfo
import com.google.gson.reflect.TypeToken
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import com.service.model.ErrorResponse
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

@LogTag("GirlViewModel")
class EpubViewModel : BaseViewModel() {

    val loadDataActionLiveData: ActionLiveData<ActionData<Int>> = ActionLiveData()
    val loadAssetActionLiveData: ActionLiveData<ActionData<ArrayList<BookInfo>>> = ActionLiveData()

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

    fun getListBookAllAsset(maxBookAsset: Int, extensionEpub: String) {
        loadAssetActionLiveData.set(ActionData(isDoing = true))

        fun getFileFromAssets(fileName: String): File? {
            val file = File(LAppResource.application.cacheDir.toString() + "/" + fileName)
            if (!file.exists()) {
                try {
                    val inputStream = LAppResource.application.assets.open("b/$fileName")
                    val size = inputStream.available()
                    val buffer = ByteArray(size)
                    inputStream.read(buffer)
                    inputStream.close()
                    val fileOutputStream = FileOutputStream(file)
                    fileOutputStream.write(buffer)
                    fileOutputStream.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return null
                }
            }
            return file
        }

        fun sortABC(listBookInfo: ArrayList<BookInfo>) {
            if (listBookInfo.isEmpty()) {
                return
            }
            listBookInfo.sortWith(
                    Comparator { bookInfo1, bookInfo2 ->
                        (bookInfo1.title ?: "").compareTo((bookInfo2.title
                                ?: ""), ignoreCase = true)
                    })
        }

        ioScope.launch {
            val listBookInfo = ArrayList<BookInfo>()

            fun searchForFiles(): ArrayList<BookInfo> {
                val listFile = ArrayList<File>()
                val infoList = ArrayList<BookInfo>()
                var file: File?
                for (i in 1..maxBookAsset) {
                    file = getFileFromAssets(fileName = "a ($i)$extensionEpub")
                    file?.let {
                        listFile.add(it)
                    }
                }
                listFile.forEach { f ->
                    val bookInfo = BookInfo()
                    bookInfo.title = f.name
                    bookInfo.filePath = f.path
                    infoList.add(bookInfo)
                }
                return infoList
            }

            val jsonBookAsset = LPrefUtil.getJsonBookAsset()
            if (jsonBookAsset.isNullOrEmpty()) {
                listBookInfo.addAll(elements = searchForFiles())
            } else {
                val tmpList = BaseApplication.gson.fromJson<List<BookInfo>>(jsonBookAsset,
                        object : TypeToken<List<BookInfo?>?>() {}.type)
                if (tmpList == null) {
                    listBookInfo.addAll(elements = searchForFiles())
                } else {
                    listBookInfo.addAll(elements = tmpList)
                }
            }

            val reader = Reader()
            for (i in listBookInfo.indices) {
                try {
                    val bookInfo = listBookInfo[i]
                    if (bookInfo.filePath == null) {
                        continue
                    }
                    reader.setInfoContent(bookInfo.filePath)
                    val title = reader.infoPackage.metadata.title
                    if (title.isNullOrEmpty()) {
                        // If txtTitle doesn't exist, use fileName instead.
                        val dotIndex = bookInfo.title?.lastIndexOf(char = '.')
                        dotIndex?.let {
                            bookInfo.title = bookInfo.title?.substring(startIndex = 0, endIndex = it)
                        }
                    } else {
                        bookInfo.title = title
                    }
                    bookInfo.coverImage = reader.coverImage
                } catch (e: ReadingException) {
                    e.printStackTrace()
                }
            }
            sortABC(listBookInfo = listBookInfo)

            loadAssetActionLiveData.post(
                    ActionData(
                            isDoing = false,
                            isSuccess = true,
                            data = listBookInfo
                    )
            )
        }
    }

}
