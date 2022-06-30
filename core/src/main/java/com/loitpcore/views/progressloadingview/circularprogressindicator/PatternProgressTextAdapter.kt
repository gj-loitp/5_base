package com.loitpcore.views.progressloadingview.circularprogressindicator

import com.loitpcore.views.progressloadingview.circularprogressindicator.CircularProgressIndicator.ProgressTextAdapter

class PatternProgressTextAdapter(private val pattern: String) : ProgressTextAdapter {

    override fun formatText(currentProgress: Double): String {
        return String.format(pattern, currentProgress)
    }
}
