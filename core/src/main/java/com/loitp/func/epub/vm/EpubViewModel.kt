package com.loitp.func.epub.vm

import android.os.Environment
import com.google.gson.reflect.TypeToken
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseApplication
import com.loitp.core.base.BaseViewModel
import com.loitp.core.utilities.LAppResource
import com.loitp.core.utilities.LPrefUtil
import com.loitp.core.utilities.LStoreUtil
import com.loitp.core.utilities.LStoreUtil.Companion.getListEpubFiles
import com.loitp.func.epub.exception.ReadingException
import com.loitp.func.epub.model.BookInfo
import com.loitp.func.epub.CssStatus
import com.loitp.func.epub.Reader
import com.loitp.service.liveData.ActionData
import com.loitp.service.liveData.ActionLiveData
import com.loitp.service.model.ErrorResponse
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("GirlViewModel")
class EpubViewModel : BaseViewModel() {

    val loadDataActionLiveData: ActionLiveData<ActionData<Int>> = ActionLiveData()
    val loadAssetActionLiveData: ActionLiveData<ActionData<ArrayList<BookInfo>>> = ActionLiveData()
    val loadDeviceAndAssetActionLiveData: ActionLiveData<ActionData<ArrayList<BookInfo>>> =
        ActionLiveData()

    fun loadData(
        reader: Reader,
        bookInfo: BookInfo
    ) {
        loadDataActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {
            var lastSavedPage = 0
            try {
                // Setting optionals once per file is enough.
                reader.setMaxContentPerSection(1250)
                // reader.setMaxContentPerSection(1250 * 10);
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

    private fun searchForFilesAsset(
        maxBookAsset: Int,
        extensionEpub: String
    ): ArrayList<BookInfo> {
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

    private fun getFileFromAssets(fileName: String): File? {
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

    fun getListBookAllAsset(
        maxBookAsset: Int,
        extensionEpub: String
    ) {
        loadAssetActionLiveData.set(ActionData(isDoing = true))

        fun sortABC(listBookInfo: ArrayList<BookInfo>) {
            if (listBookInfo.isEmpty()) {
                return
            }
            listBookInfo.sortWith { bookInfo1, bookInfo2 ->
                (bookInfo1.title ?: "").compareTo(
                    (
                            bookInfo2.title
                                ?: ""
                            ),
                    ignoreCase = true
                )
            }
        }

        ioScope.launch {
            val listBookInfo = ArrayList<BookInfo>()

            val jsonBookAsset = LPrefUtil.getJsonBookAsset()
            if (jsonBookAsset.isNullOrEmpty()) {
                listBookInfo.addAll(
                    elements = searchForFilesAsset(
                        maxBookAsset = maxBookAsset,
                        extensionEpub = extensionEpub
                    )
                )
            } else {
                val tmpList = BaseApplication.gson.fromJson<List<BookInfo>>(
                    jsonBookAsset,
                    object : TypeToken<List<BookInfo?>?>() {}.type
                )
                if (tmpList == null) {
                    listBookInfo.addAll(
                        elements = searchForFilesAsset(
                            maxBookAsset = maxBookAsset,
                            extensionEpub = extensionEpub
                        )
                    )
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
                            bookInfo.title =
                                bookInfo.title?.substring(startIndex = 0, endIndex = it)
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

    private fun searchForFilesDeviceAndAsset(): ArrayList<BookInfo> {
        val isSDPresent = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        val listBookInfo = ArrayList<BookInfo>()
        if (isSDPresent) {
            val listFile =
                getListEpubFiles(File(Environment.getExternalStorageDirectory().absolutePath))

            // only get 1 file from asset
            val sampleFile = LStoreUtil.getFileFromAssets(fileName = "a (1).sqlite")
            sampleFile?.let {
                listFile.add(index = 0, element = it)
            }

            listFile.forEach { file ->
                val bookInfo = BookInfo()
                bookInfo.title = file.name
                bookInfo.filePath = file.path
                listBookInfo.add(bookInfo)
            }
        }
        return listBookInfo
    }

    fun getListBookFromDeviceAndAsset() {
        loadDeviceAndAssetActionLiveData.set(ActionData(isDoing = true))
        ioScope.launch {
            val listBookInfo = searchForFilesDeviceAndAsset()
            val reader = Reader()
            for (bookInfo in listBookInfo) {
                try {
                    reader.setInfoContent(bookInfo.filePath)
                    val title = reader.infoPackage.metadata.title
                    if (title.isNullOrEmpty()) {
                        // If title doesn't exist, use fileName instead.
                        val dotIndex = bookInfo.title?.lastIndexOf(char = '.')
                        dotIndex?.let {
                            bookInfo.title =
                                bookInfo.title?.substring(startIndex = 0, endIndex = it)
                        }
                    } else {
                        bookInfo.title = reader.infoPackage.metadata.title
                    }
                    bookInfo.coverImage = reader.coverImage
                } catch (e: ReadingException) {
                    e.printStackTrace()
                }
            }
            loadDeviceAndAssetActionLiveData.post(
                ActionData(
                    isDoing = false,
                    isSuccess = true,
                    data = listBookInfo
                )
            )
        }
    }
}
