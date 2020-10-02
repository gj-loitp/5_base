package vn.loitp.app.activity.function.downloadmanager

import com.annotation.LogTag
import com.core.base.BaseViewModel
import com.core.utilities.LStoreUtil
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import kotlinx.coroutines.launch
import java.io.File

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

@LogTag("loitppLStoreUtilModel")
class LStoreUtilModel : BaseViewModel() {

    val writeToFileActionLiveData: ActionLiveData<ActionData<File>> = ActionLiveData()
    val readTxtFromFolderActionLiveData: ActionLiveData<ActionData<String>> = ActionLiveData()
    val readTxtFromRawFolderActionLiveData: ActionLiveData<ActionData<String>> = ActionLiveData()

    fun writeToFile(folder: String, fileName: String, body: String) {
        writeToFileActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {

            val fileSaved = LStoreUtil.writeToFile(
                    folder = folder,
                    fileName = fileName,
                    body = body)
            if (fileSaved == null) {
                writeToFileActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = false
                        )
                )
            } else {
                writeToFileActionLiveData.post(
                        ActionData(
                                isDoing = false,
                                isSuccess = true,
                                data = fileSaved
                        )
                )
            }
        }

    }

    fun readTxtFromFolder(folderName: String, fileName: String) {
        readTxtFromFolderActionLiveData.set(ActionData(isDoing = true))
        logD(">>>readTxtFromFolder folderName $folderName, fileName $fileName")
        ioScope.launch {

            val string = LStoreUtil.readTxtFromFolder(
                    folderName = folderName,
                    fileName = fileName
            )
            logD("<<<readTxtFromFolder string $string")
            readTxtFromFolderActionLiveData.post(
                    ActionData(
                            isDoing = false,
                            isSuccess = true,
                            data = string
                    )
            )
        }

    }

    fun readTxtFromRawFolder(nameOfRawFile: Int) {
        readTxtFromRawFolderActionLiveData.set(ActionData(isDoing = true))
        logD(">>>readTxtFromRawFolderActionLiveData nameOfRawFile $nameOfRawFile")
        ioScope.launch {

            val string = LStoreUtil.readTxtFromRawFolder(
                    nameOfRawFile = nameOfRawFile
            )
            logD("<<<readTxtFromRawFolderActionLiveData string $string")
            readTxtFromRawFolderActionLiveData.post(
                    ActionData(
                            isDoing = false,
                            isSuccess = true,
                            data = string
                    )
            )
        }

    }
}
