package com.loitpcore.views.progressLoadingView.circularProgressIndicator

import com.loitpcore.views.progressLoadingView.circularProgressIndicator.CircularProgressIndicator.ProgressTextAdapter

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class PatternProgressTextAdapter(private val pattern: String) : ProgressTextAdapter {

    override fun formatText(currentProgress: Double): String {
        return String.format(pattern, currentProgress)
    }
}
