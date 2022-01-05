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
