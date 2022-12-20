package com.loitpcore.data

import com.loitp.core.common.Constants

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class ActivityData {

    companion object {
        val instance: ActivityData by lazy(LazyThreadSafetyMode.PUBLICATION) { ActivityData() }
    }

    var type = Constants.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
}
