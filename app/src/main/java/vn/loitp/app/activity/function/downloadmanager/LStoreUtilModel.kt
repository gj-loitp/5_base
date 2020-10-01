package vn.loitp.app.activity.function.downloadmanager

import com.annotation.LogTag
import com.core.base.BaseViewModel
import com.service.livedata.ActionData
import com.service.livedata.ActionLiveData
import java.io.File

/**
 * Created by Loitp on 24,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

@LogTag("LStoreUtilModel")
class LStoreUtilModel : BaseViewModel() {

    val writeToFileActionLiveData: ActionLiveData<ActionData<File>> = ActionLiveData()

    fun writeToFile() {
        writeToFileActionLiveData.set(ActionData(isDoing = true))

//        ioScope.launch {
//
//            LStoreUtil.writeToFile()
//            writeToFileActionLiveData.post(
//                    ActionData(
//                            isDoing = false,
//                            isSuccess = true,
//                            data = data
//                    )
//            )
//
//        }

    }
}
