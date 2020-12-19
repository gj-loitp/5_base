package com.views.progressloadingview.circularprogressindicator

import com.views.progressloadingview.circularprogressindicator.CircularProgressIndicator.ProgressTextAdapter

/**
 * Created by Anton on 06.06.2018.
 */
class DefaultProgressTextAdapter : ProgressTextAdapter {
    override fun formatText(currentProgress: Double): String {
        return currentProgress.toString()
    }
}
