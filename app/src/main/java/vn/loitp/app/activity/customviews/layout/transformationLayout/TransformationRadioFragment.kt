package vn.loitp.app.activity.customviews.layout.transformationLayout

import android.os.Bundle
import android.view.View
import com.loitp.core.base.BaseFragment
import com.loitp.core.ext.setSafeOnClickListener
import kotlinx.android.synthetic.main.frm_transformation_radio.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.transformationLayout.recycler.PosterCircleAdapter

class TransformationRadioFragment : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.frm_transformation_radio
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
