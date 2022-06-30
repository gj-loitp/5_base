package com.loitpcore.views.progressloadingview.circularprogressindicator

import com.loitpcore.views.progressloadingview.circularprogressindicator.CircularProgressIndicator.ProgressTextAdapter

class DefaultProgressTextAdapter : ProgressTextAdapter {
    override fun formatText(currentProgress: Double): String {
        return currentProgress.toString()
    }
}
