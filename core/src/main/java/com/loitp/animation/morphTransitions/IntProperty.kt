package com.loitp.animation.morphTransitions

import android.util.Property

internal abstract class IntProperty<T>(name: String) : Property<T, Int>(Int::class.java, name) {

    abstract fun setValue(obj: T, value: Int)

    override fun set(obj: T, value: Int) {
        setValue(obj, value)
    }
}
