package com.picker.imagepickerwithcrop

class GlobalHolder private constructor() {

    companion object {
        val instance = GlobalHolder()
    }

    var pickerManager: PickerManager? = null
}
