package vn.loitp.app.activity.customviews.layout.transformationLayout.single

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import vn.loitp.R

@LogTag("MainSingleActivity")
@IsFullScreen(false)
class TransformationSingleActivity : BaseFontActivity() {
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_layout_transformation_single_main
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
