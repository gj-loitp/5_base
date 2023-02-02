package vn.loitp.up.a.demo.architectureComponent.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ColorChangerViewModel : ViewModel() {
    val colorResource = MutableLiveData<Int>()

    init {
        colorResource.value = 0xfff
    }
}
