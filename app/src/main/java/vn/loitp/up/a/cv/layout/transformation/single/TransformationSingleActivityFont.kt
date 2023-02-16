package vn.loitp.up.a.cv.layout.transformation.single

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import vn.loitp.R

@LogTag("MainSingleActivity")
@IsFullScreen(false)
class TransformationSingleActivityFont : BaseActivityFont() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_transformation_single_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
