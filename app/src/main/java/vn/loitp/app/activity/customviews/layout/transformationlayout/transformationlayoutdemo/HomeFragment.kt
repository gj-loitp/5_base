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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_transformation_home.*
import vn.loitp.app.activity.customviews.layout.transformationlayout.transformationlayoutdemo.MockUtil.getMockPosters
import vn.loitp.app.activity.customviews.layout.transformationlayout.transformationlayoutdemo.recycler.PosterAdapter
import vn.loitp.app.activity.customviews.layout.transformationlayout.transformationlayoutdemo.recycler.PosterMenuAdapter
import vn.loitp.app.R

class HomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_transformation_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = PosterAdapter().apply { addPosterList(getMockPosters()) }
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
            Toast.makeText(context, "Compose New", Toast.LENGTH_SHORT).show()
        }
    }
}
