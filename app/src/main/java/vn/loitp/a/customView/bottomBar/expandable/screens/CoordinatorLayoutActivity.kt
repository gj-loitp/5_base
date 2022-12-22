package vn.loitp.a.customView.bottomBar.expandable.screens

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_ebb_coordinator_layout.*
import vn.loitp.R

@LogTag("CoordinatorLayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class CoordinatorLayoutActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_ebb_coordinator_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setupViews()
    }

    private fun setupViews() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Meow", Snackbar.LENGTH_LONG).show()
        }
    }
}
