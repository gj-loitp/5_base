package com.picker.imagepickerwithcrop;

/**
 * Created by Mickael on 18/10/2016.
 */

//TODO convert kotlin
public class GlobalHolder {

    private PickerManager pickerManager;

    private static GlobalHolder ourInstance = new GlobalHolder();

    public static GlobalHolder getInstance() {
        return ourInstance;
    }

    private GlobalHolder() {
    }


    public PickerManager getPickerManager() {
        return pickerManager;
    }

    public void setPickerManager(final PickerManager pickerManager) {
        this.pickerManager = pickerManager;
    }
}
