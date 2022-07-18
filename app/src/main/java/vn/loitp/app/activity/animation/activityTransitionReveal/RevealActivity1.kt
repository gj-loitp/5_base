package vn.loitp.app.activity.animation.activityTransitionReveal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
import com.tombayley.activitycircularreveal.CircularReveal
import kotlinx.android.synthetic.main.activity_reveal_1.*
import vn.loitp.app.R

@LogTag("MainActivity")
@IsFullScreen(false)
class RevealActivity1 : BaseFontActivity() {

    companion object {
        const val REQUEST_CODE = 69
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_reveal_1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = RevealActivity1::class.java.simpleName
        }
        fab.setSafeOnClickListener {
            show(it)
        }
        tv.setSafeOnClickListener {
            show(it)
        }
        iv.setSafeOnClickListener {
            show(it)
        }
        bt.setSafeOnClickListener {
            show(it)
        }
        ib.setSafeOnClickListener {
            show(it)
        }
    }

    private fun show(view: View) {
        val builder = CircularReveal.Builder(
            this,
            view,
            Intent(this, RevealActivity2::class.java),
            1000
        ).apply {
            revealColor = ContextCompat.getColor(
                this@RevealActivity1,
                R.color.green
            )
            requestCode = REQUEST_CODE
        }

        CircularReveal.presentActivity(builder)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val msg = data?.getStringExtra(RevealActivity2.KEY_DATA_RETURN)
            showShortInformation("onActivityResult REQUEST_CODE $REQUEST_CODE -> msg $msg")
        }
    }
}
