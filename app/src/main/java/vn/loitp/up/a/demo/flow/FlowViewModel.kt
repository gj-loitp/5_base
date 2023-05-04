package vn.loitp.up.a.demo.flow

import androidx.lifecycle.MutableLiveData
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    private val _timeStateWithDefaultValue = MutableStateFlow(System.currentTimeMillis().toString())
    val timeStateWithDefaultValue: StateFlow<String> = _timeStateWithDefaultValue.asStateFlow()

    private val _timeStateNoDefaultValue = MutableStateFlow(System.currentTimeMillis().toString())
    val timeStateNoDefaultValue: StateFlow<String> = _timeStateNoDefaultValue.asStateFlow()

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

    fun updateTimeStateWithDefaultValue() {
        ioScope.launch {
            _timeStateWithDefaultValue.emit(System.currentTimeMillis().toString())
//            _timeState.value = System.currentTimeMillis().toString()
        }
    }

    fun updateTimeStateNoDefaultValue() {
        ioScope.launch {
            _timeStateNoDefaultValue.emit(System.currentTimeMillis().toString())
        }
    }
}
