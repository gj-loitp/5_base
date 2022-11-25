package vn.loitp.app.activity.customviews.bottomBar.expandableBottomBar.screens

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_ebb_coordinator_layout.*
import vn.loitp.app.R

@LogTag("CoordinatorLayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CoordinatorLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_ebb_coordinator_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Meow", Snackbar.LENGTH_LONG).show()
        }
    }

}
