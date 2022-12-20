package vn.loitp.app.activity.customviews.layout.transformationLayout.single

import android.os.Bundle
import android.view.View
import com.loitp.core.base.BaseFragment
import com.loitp.core.utilities.LImageUtil
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationEndContainer
import kotlinx.android.synthetic.main.activity_layout_transformation_detail.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.layout.transformationLayout.recycler.Poster

class TransformationSingleDetailFragment : BaseFragment() {

    companion object {
        const val TAG = "MainSingleDetailFragment"
        const val posterKey = "posterKey"
        const val paramsKey = "paramsKey"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_transformation_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // [Step1]: apply onTransformationEndContainer using TransformationLayout.Params.
        //TODO getParcelable
        val params = arguments?.getParcelable<TransformationLayout.Params>(paramsKey)
        onTransformationEndContainer(params)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val poster = arguments?.getParcelable<Poster>(posterKey)
        poster?.let {

            // [Step2]: sets a transition name to the target view.
            layoutDetailContainer.transitionName = poster.name

            LImageUtil.load(
                context = context,
                any = it.poster,
                imageView = ivProfileDetailBackground
            )

            tvDetailTitle.text = it.name
            tvDetailDescription.text = it.description
        }
    }
}
