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

package vn.loitp.app.activity.customviews.layout.transformationlayout.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.addTransformation
import com.skydoves.transformationlayout.onTransformationStartContainer
import kotlinx.android.synthetic.main.fragment_transformation_home.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.transformationlayout.MockUtil.getMockPosters
import vn.loitp.app.activity.customviews.layout.transformationlayout.recycler.Poster
import vn.loitp.app.activity.customviews.layout.transformationlayout.recycler.PosterMenuAdapter
import vn.loitp.app.activity.customviews.layout.transformationlayout.recycler.PosterSingleAdapter

class TransformationSingleFragment : Fragment(), PosterSingleAdapter.PosterDelegate {

    companion object {
        const val TAG = "MainSingleFragment"
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transformation_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // [Step1]: apply onTransformationStartContainer.
        onTransformationStartContainer()
    }

    /** This function will be called from the [PosterSingleAdapter.PosterDelegate]'s onBindViewHolder. */
    override fun onItemClick(poster: Poster, itemView: TransformationLayout) {
        val fragment = TransformationSingleDetailFragment()
        // [Step2]: getBundle from the TransformationLayout.
        val bundle = itemView.getBundle(TransformationSingleDetailFragment.paramsKey)
        bundle.putParcelable(TransformationSingleDetailFragment.posterKey, poster)
        fragment.arguments = bundle

        requireFragmentManager()
                .beginTransaction()
                // [Step3]: addTransformation using the TransformationLayout.
                .addTransformation(itemView)
                .replace(R.id.layoutContainer, fragment, TransformationSingleDetailFragment.TAG)
                .addToBackStack(TransformationSingleDetailFragment.TAG)
                .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = PosterSingleAdapter(this).apply { addPosterList(getMockPosters()) }
        recyclerViewMenu.adapter = PosterMenuAdapter().apply { addPosterList(getMockPosters()) }

        fab.setOnClickListener {
            if (!transformationLayout.isTransforming) {
                backgroundView.visibility = View.VISIBLE
            }
            transformationLayout.startTransform()
        }

        menuHome.setOnClickListener {
            if (!transformationLayout.isTransforming) {
                backgroundView.visibility = View.GONE
            }
            transformationLayout.finishTransform()
        }
    }
}
