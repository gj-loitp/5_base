package com.animation.morphtransitions

import android.util.Property

internal abstract class FloatProperty<T>(name: String?)
    : Property<T, Float>(Float::class.java, name) {
    /**
     * A type-specific override of the [.set] that is faster when dealing
     * with fields of type `float`.
     */
    abstract fun setValue(obj: T, value: Float)

    override fun set(obj: T, value: Float) {
        setValue(obj, value)
    }
}
