package vn.loitp.app.activity.customviews.layout.transformationlayout

import android.os.Bundle
import android.view.View
import com.core.base.BaseFragment
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.fragment_transformation_library.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.transformationlayout.TransformationMockUtil.getMockPosters
import vn.loitp.app.activity.customviews.layout.transformationlayout.recycler.PosterLineAdapter

class TransformationLibraryFragment : BaseFragment() {
    override fun setLayoutResourceId(): Int {
        return R.layout.fragment_transformation_library
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        recyclerView.adapter = PosterLineAdapter().apply {
            addPosterList(getMockPosters())
        }

        fab.setSafeOnClickListener {
            transformationLayout.startTransform()
        }

        layoutMenuCard.setSafeOnClickListener {
            transformationLayout.finishTransform()
            showShortInformation("Play")
        }
    }
}
