package vn.loitp.app.activity.function.downloadmanager

import com.annotation.LogTag
import com.core.base.BaseViewModel
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import kotlinx.coroutines.launch

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

@LogTag("LStoreUtilModel")
class LStoreUtilModel : BaseViewModel() {

    val writeToFileActionLiveData: ActionLiveData<ActionData<String>> = ActionLiveData()

    fun writeToFile(folder: String, fileName: String, body: String) {
        writeToFileActionLiveData.set(ActionData(isDoing = true))

        ioScope.launch {

//            val isSuccess = LStoreUtil.writeToFile(
//                    folder = folder,
//                    fileName = fileName,
//                    body = body)
//            if (isSuccess) {
//                writeToFileActionLiveData.post(
//                        ActionData(
//                                isDoing = false,
//                                isSuccess = true,
//                                data = body
//                        )
//                )
//            } else {
//                writeToFileActionLiveData.post(
//                        ActionData(
//                                isDoing = false,
//                                isSuccess = false
//                        )
//                )
//            }
        }

    }
}
