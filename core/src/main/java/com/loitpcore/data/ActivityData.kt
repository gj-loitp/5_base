package com.loitpcore.data

import com.loitpcore.core.common.Constants

class ActivityData {

    companion object {
        val instance: ActivityData by lazy(LazyThreadSafetyMode.PUBLICATION) { ActivityData() }
    }

    var type = Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
}
