package com.loitpcore.views.progressLoadingView.circularProgressIndicator

import com.loitpcore.views.progressLoadingView.circularProgressIndicator.CircularProgressIndicator.ProgressTextAdapter

class PatternProgressTextAdapter(private val pattern: String) : ProgressTextAdapter {

    override fun formatText(currentProgress: Double): String {
        return String.format(pattern, currentProgress)
    }
}
