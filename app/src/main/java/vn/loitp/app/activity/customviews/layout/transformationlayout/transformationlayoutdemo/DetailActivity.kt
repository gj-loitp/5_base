/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vn.loitp.app.activity.customviews.layout.transformationlayout.transformationlayoutdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.bumptech.glide.Glide
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationEndContainer
import kotlinx.android.synthetic.main.activity_layout_transformation.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.transformationlayout.transformationlayoutdemo.recycler.Poster

@LogTag("DetailActivity")
@IsFullScreen(false)
class DetailActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_transformation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainer(intent.getParcelableExtra(Constants.activityTransitionName))
        super.onCreate(savedInstanceState)

        intent.getParcelableExtra<Poster>(posterExtraName)?.let {
            Glide.with(this)
                    .load(it.poster)
                    .into(ivProfileDetailBackground)
            tvDetailTitle.text = it.name
            tvDetailDescription.text = it.description
        }
    }

    companion object {
        const val posterExtraName = "posterExtraName"
        fun startActivity(
                context: Context,
                transformationLayout: TransformationLayout,
                poster: Poster
        ) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(posterExtraName, poster)
            TransformationCompat.startActivity(transformationLayout, intent)
        }
    }
}