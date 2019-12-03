package vn.loitp.app.activity.demo.architecturecomponent.viewmodel

import androidx.lifecycle.ViewModel

class ColorChangerViewModel : ViewModel() {
    private var colorResource: Int = 0xfff

    fun setColorResource(colorResource: Int) {
        this.colorResource = colorResource
    }

    fun getColorResource(): Int {
        return colorResource
    }
}
