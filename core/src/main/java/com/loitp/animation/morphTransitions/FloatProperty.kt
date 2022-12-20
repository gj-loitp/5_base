package com.loitp.animation.morphTransitions

import android.util.Property

internal abstract class FloatProperty<T>(name: String?) :
    Property<T, Float>(Float::class.java, name) {
    abstract fun setValue(obj: T, value: Float)

    override fun set(obj: T, value: Float) {
        setValue(obj, value)
    }
}
