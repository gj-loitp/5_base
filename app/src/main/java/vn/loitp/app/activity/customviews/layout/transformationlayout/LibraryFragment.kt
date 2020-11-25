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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_library.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.transformationlayout.MockUtil.getMockPosters
import vn.loitp.app.activity.customviews.layout.transformationlayout.recycler.PosterLineAdapter

class LibraryFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = PosterLineAdapter().apply { addPosterList(getMockPosters()) }

        fab.setOnClickListener {
            transformationLayout.startTransform()
        }

        menu_card.setOnClickListener {
            transformationLayout.finishTransform()
            Toast.makeText(context, "Play", Toast.LENGTH_SHORT).show()
        }
    }
}
