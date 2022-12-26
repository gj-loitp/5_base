package com.loitp.views.loading.circularProgressIndicator

import com.loitp.views.loading.circularProgressIndicator.CircularProgressIndicator.ProgressTextAdapter

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class DefaultProgressTextAdapter : ProgressTextAdapter {
    override fun formatText(currentProgress: Double): String {
        return currentProgress.toString()
    }
}
