package vn.loitp.a.cv.layout.transformation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.common.Constants
import com.loitp.core.utilities.LImageUtil
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationEndContainer
import kotlinx.android.synthetic.main.a_layout_transformation_detail.*
import vn.loitp.R
import vn.loitp.a.cv.layout.transformation.rv.Poster

@LogTag("DetailActivity")
@IsFullScreen(false)
class TransformationDetailActivity : BaseFontActivity() {

    companion object {
        const val posterExtraName = "posterExtraName"
        fun startActivity(
            context: Context,
            transformationLayout: TransformationLayout,
            poster: Poster
        ) {
            val intent = Intent(context, TransformationDetailActivity::class.java)
            intent.putExtra(posterExtraName, poster)
            TransformationCompat.startActivity(transformationLayout, intent)
        }
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_transformation_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainer(intent.getParcelableExtra(Constants.activityTransitionName))
        super.onCreate(savedInstanceState)

        intent.getParcelableExtra<Poster>(posterExtraName)?.let {
            LImageUtil.load(context = this, any = it.poster, imageView = ivProfileDetailBackground)
            tvDetailTitle.text = it.name
            tvDetailDescription.text = it.description
        }
    }
}
