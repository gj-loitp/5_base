package com.interfaces

/**
 * Created by Loitp on 18,December,2019
 * HMS Ltd
 * Ho Chi Minh City, VN
 * www.muathu@gmail.com
 */

interface CallbackPull {
    fun onUpOrLeft(offset: Float)

    fun onUpOrLeftRefresh(offset: Float)

    fun onDownOrRight(offset: Float)

    fun onDownOrRightRefresh(offset: Float)
}
