package vn.loitp.up.a.cv.layout.transformation.single

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import vn.loitp.R
import vn.loitp.databinding.ALayoutTransformationSingleMainBinding

@LogTag("MainSingleActivity")
@IsFullScreen(false)
class TransformationSingleActivity : BaseActivityFont() {

    private lateinit var binding: ALayoutTransformationSingleMainBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALayoutTransformationSingleMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.layoutContainer,
                TransformationSingleFragment(),
                TransformationSingleFragment.TAG
            )
            .commit()
    }
}
