/*
 * Copyright 2014 Toxic Bakery
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.loitpcore.views.viewpager.viewpagertransformers

import android.view.View
import kotlin.math.abs

class ZoomOutTranformer : BaseTransformer() {

    override fun onTransform(view: View, position: Float) {
        val scale = 1f + abs(position)
        view.scaleX = scale
        view.scaleY = scale
        view.pivotX = view.width * 0.5f
        view.pivotY = view.height * 0.5f
        view.alpha = if (position < -1f || position > 1f) 0f else 1f - (scale - 1f)
        if (position == -1f) {
            view.translationX = (view.width * -1).toFloat()
        }
    }
}
