package com.loitpcore.views.shadowViewHelper

import java.io.Serializable
import kotlin.math.max

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ShadowProperty : Serializable {

    /**
     * 阴影颜色
     */
    private var shadowColor: Int = 0

    /**
     * 阴影半径
     */
    private var shadowRadius: Int = 0

    /**
     * 阴影x偏移
     */
    private var shadowDx: Int = 0

    /**
     * 阴影y偏移
     */
    private var shadowDy: Int = 0

    /**
     * 阴影边
     */
    private var shadowSide = ALL

    val shadowOffset: Int
        get() = shadowOffsetHalf * 2

    private val shadowOffsetHalf: Int
        get() = if (0 >= shadowRadius) 0 else max(shadowDx, shadowDy) + shadowRadius

    fun getShadowSide(): Int {
        return shadowSide
    }

    fun setShadowSide(shadowSide: Int): ShadowProperty {
        this.shadowSide = shadowSide
        return this
    }

    fun getShadowColor(): Int {
        return shadowColor
    }

    fun setShadowColor(shadowColor: Int): ShadowProperty {
        this.shadowColor = shadowColor
        return this
    }

    fun getShadowRadius(): Int {
        return shadowRadius
    }

    fun setShadowRadius(shadowRadius: Int): ShadowProperty {
        this.shadowRadius = shadowRadius
        return this
    }

    fun getShadowDx(): Int {
        return shadowDx
    }

    @Suppress("unused")
    fun setShadowDx(shadowDx: Int): ShadowProperty {
        this.shadowDx = shadowDx
        return this
    }

    fun getShadowDy(): Int {
        return shadowDy
    }

    fun setShadowDy(shadowDy: Int): ShadowProperty {
        this.shadowDy = shadowDy
        return this
    }

    companion object {
        const val ALL = 0x1111
        const val LEFT = 0x0001
        const val TOP = 0x0010
        const val RIGHT = 0x0100
        const val BOTTOM = 0x1000
    }
}
