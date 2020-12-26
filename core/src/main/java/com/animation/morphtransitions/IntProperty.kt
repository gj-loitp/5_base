package com.animation.morphtransitions

import android.util.Property

internal abstract class IntProperty<T>(name: String)
    : Property<T, Int>(Int::class.java, name) {

    /**
     * A type-specific override of the [.set] that is faster when dealing
     * with fields of type `int`.
     */
    abstract fun setValue(obj: T, value: Int)

    override fun set(obj: T, value: Int) {
        setValue(obj, value)
    }
}
