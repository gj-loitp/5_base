package com.loitp.data

import com.loitp.core.common.TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT

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

    var type = TYPE_ACTIVITY_TRANSITION_SYSTEM_DEFAULT
}
