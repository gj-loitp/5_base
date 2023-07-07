package vn.loitp.up.a.cv.layout.transformation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsShowAnimWhenExit
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.common.activityTransitionName
import com.loitp.core.ext.loadGlide
import com.skydoves.transformationlayout.TransformationCompat
import com.skydoves.transformationlayout.TransformationLayout
import com.skydoves.transformationlayout.onTransformationEndContainer
import vn.loitp.databinding.ALayoutTransformationDetailBinding
import vn.loitp.up.a.cv.layout.transformation.rv.Poster

@LogTag("DetailActivity")
@IsFullScreen(false)
@IsShowAnimWhenExit(false)
class TransformationDetailActivity : BaseActivityFont() {

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

    private lateinit var binding: ALayoutTransformationDetailBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainer(intent.getParcelableExtra(activityTransitionName))
        super.onCreate(savedInstanceState)

        binding = ALayoutTransformationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<Poster>(posterExtraName)?.let {
            binding.ivProfileDetailBackground.loadGlide(any = it.poster)
            binding.tvDetailTitle.text = it.name
            binding.tvDetailDescription.text = it.description
        }
    }
}
