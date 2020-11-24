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

package vn.loitp.app.activity.customviews.layout.transformationlayout.transformationlayoutdemo.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.core.utilities.LImageUtil
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationEndContainer
import kotlinx.android.synthetic.main.activity_layout_transformation.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.transformationlayout.transformationlayoutdemo.recycler.Poster

class TransformationSingleDetailFragment : Fragment() {

    companion object {
        const val TAG = "MainSingleDetailFragment"
        const val posterKey = "posterKey"
        const val paramsKey = "paramsKey"
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_layout_transformation, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // [Step1]: apply onTransformationEndContainer using TransformationLayout.Params.
        val params = arguments?.getParcelable<TransformationLayout.Params>(paramsKey)
        onTransformationEndContainer(params)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val poster = arguments?.getParcelable<Poster>(posterKey)
        poster?.let {

            // [Step2]: sets a transition name to the target view.
            layoutDetailContainer.transitionName = poster.name

            LImageUtil.load(context = context, any = it.poster, imageView = ivProfileDetailBackground)

            tvDetailTitle.text = it.name
            tvDetailDescription.text = it.description
        }
    }
}
