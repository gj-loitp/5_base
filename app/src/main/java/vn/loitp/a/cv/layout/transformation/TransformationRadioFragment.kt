package vn.loitp.a.cv.layout.transformation

import android.os.Bundle
import android.view.View
import com.loitp.core.base.BaseFragment
import com.loitp.core.ext.setSafeOnClickListener
import kotlinx.android.synthetic.main.f_transformation_radio.*
import vn.loitp.R
import vn.loitp.a.cv.layout.transformation.rv.PosterCircleAdapter

class TransformationRadioFragment : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.f_transformation_radio
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
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
