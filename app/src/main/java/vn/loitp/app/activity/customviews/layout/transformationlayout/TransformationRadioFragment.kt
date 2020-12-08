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

package vn.loitp.app.activity.customviews.layout.transformationlayout

import android.os.Bundle
import android.view.View
import com.core.base.BaseFragment
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_transformation_radio.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.transformationlayout.recycler.PosterCircleAdapter

class TransformationRadioFragment : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.fragment_transformation_radio
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        recyclerView.adapter = PosterCircleAdapter().apply {
            addPosterList(TransformationMockUtil.getMockPosters())
        }

        fab.setSafeOnClickListener {
            TransformationDetailActivity.startActivity(
                    requireContext(),
                    transformationLayoutFab,
                    TransformationMockUtil.getMockPoster()
            )
        }
    }
}
