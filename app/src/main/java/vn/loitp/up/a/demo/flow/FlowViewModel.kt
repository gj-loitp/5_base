package vn.loitp.up.a.demo.flow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseApplication
import com.loitp.core.base.BaseViewModel
import com.loitp.core.helper.ttt.db.TTTDatabase
import com.loitp.core.helper.ttt.model.chap.Chap
import com.loitp.core.helper.ttt.model.chap.Chaps
import com.loitp.core.helper.ttt.model.chap.Info
import com.loitp.core.helper.ttt.model.chap.TTTChap
import com.loitp.core.helper.ttt.model.comic.Comic
import com.loitp.core.helper.ttt.model.comictype.ComicType
import com.loitp.sv.liveData.ActionData
import com.loitp.sv.liveData.ActionLiveData
import com.loitp.sv.liveData.QueuedMutableLiveData
import com.loitp.sv.liveData.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.* // ktlint-disable no-wildcard-imports

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@LogTag("FlowViewModel")
class FlowViewModel : BaseViewModel() {
    val nameLiveEvent: MutableLiveData<String> = MutableLiveData()
    private val _countState = MutableStateFlow(0)
    val countState: StateFlow<Int> = _countState

    fun setName(s: String) {
        ioScope.launch {
            nameLiveEvent.postValue(s)
        }
    }

    fun incrementCount() {
        ioScope.launch {
            _countState.value++
        }
    }
}
