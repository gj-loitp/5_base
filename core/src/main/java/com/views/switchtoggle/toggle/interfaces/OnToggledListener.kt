package com.views.switchtoggle.toggle.interfaces

import com.views.switchtoggle.toggle.LabeledSwitch

interface OnToggledListener {
    fun onSwitched(labeledSwitch: LabeledSwitch, isOn: Boolean)
}
