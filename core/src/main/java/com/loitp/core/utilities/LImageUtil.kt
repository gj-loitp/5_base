package com.loitp.core.utilities

import com.ortiz.touchview.TouchImageView

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
// https://github.com/wasabeef/glide-transformations
class LImageUtil {
    companion object {
        private const val logTag = "LImageUtil"















        fun setZoomFitWidthScreen(touchImageView: TouchImageView?) {
            touchImageView?.post {
                val maxZoomRatio =
                    LScreenUtil.screenWidth.toFloat() / LUIUtil.getWidthOfView(touchImageView)
                        .toFloat()
                touchImageView.setMaxZoomRatio(maxZoomRatio)
            }
        }
    }
}
