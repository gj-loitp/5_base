package vn.loitp.a.anim.activityTransitionReveal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.tombayley.activitycircularreveal.CircularReveal
import kotlinx.android.synthetic.main.a_reveal_1.*
import vn.loitp.R

@LogTag("MainActivity")
@IsFullScreen(false)
class RevealActivity1Font : BaseActivityFont() {

    companion object {
        const val REQUEST_CODE = 69
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.a_reveal_1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = RevealActivity1Font::class.java.simpleName
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
            activity = this,
            viewClicked = view,
            intent = Intent(this, RevealActivity2Font::class.java),
            duration = 1000
        ).apply {
            revealColor = ContextCompat.getColor(
                this@RevealActivity1Font,
                R.color.green
            )
            requestCode = REQUEST_CODE
        }

        CircularReveal.presentActivity(builder)
    }

    //TODO fix onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val msg = data?.getStringExtra(RevealActivity2Font.KEY_DATA_RETURN)
            showShortInformation("onActivityResult REQUEST_CODE $REQUEST_CODE -> msg $msg")
        }
    }
}
