package vn.loitp.up.a.cv.layout.transformation

import android.os.Bundle
import android.os.SystemClock
import android.view.View
import com.loitp.core.base.BaseFragment
import com.loitp.core.ext.setSafeOnClickListener
import kotlinx.android.synthetic.main.f_transformation_home.*
import vn.loitp.R
import vn.loitp.up.a.cv.layout.transformation.TransformationMockUtil.getMockPosters
import vn.loitp.up.a.cv.layout.transformation.rv.PosterAdapter
import vn.loitp.up.a.cv.layout.transformation.rv.PosterMenuAdapter

class TransformationHomeFragment : BaseFragment() {

    override fun setLayoutResourceId(): Int {
        return R.layout.f_transformation_home
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private var previousTime = SystemClock.elapsedRealtime()
    private fun setupViews() {
        recyclerView.adapter = PosterAdapter { poster, layoutItemPosterTransformation ->
            context?.let { c ->
                val now = SystemClock.elapsedRealtime()
                if (now - previousTime >= layoutItemPosterTransformation.duration) {
                    TransformationDetailActivity.startActivity(
                        c,
                        layoutItemPosterTransformation,
                        poster
                    )
                    previousTime = now
                }
            }
        }.apply {
            addPosterList(getMockPosters())
        }
        recyclerViewMenu.adapter = PosterMenuAdapter().apply {
            addPosterList(getMockPosters())
        }

        fab.setSafeOnClickListener {
            if (!transformationLayout.isTransforming) {
                backgroundView.visibility = View.VISIBLE
            }
            transformationLayout.startTransform()
        }

        menuHome.setSafeOnClickListener {
            if (!transformationLayout.isTransforming) {
                backgroundView.visibility = View.GONE
            }
            transformationLayout.finishTransform()
            showShortInformation("Compose New")
        }
    }
}
