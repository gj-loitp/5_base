package com.loitpcore.views.progressLoadingView.circularProgressIndicator

import com.loitpcore.views.progressLoadingView.circularProgressIndicator.CircularProgressIndicator.ProgressTextAdapter

class DefaultProgressTextAdapter : ProgressTextAdapter {
    override fun formatText(currentProgress: Double): String {
        return currentProgress.toString()
    }
}
