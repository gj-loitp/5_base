package com.data

import com.core.common.Constants

/**
 * Created by www.muathu@gmail.com on 1/4/2018.
 */

class ActivityData {

    companion object {
        val instance: ActivityData by lazy(LazyThreadSafetyMode.PUBLICATION) { ActivityData() }
    }

    var type = Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
}
