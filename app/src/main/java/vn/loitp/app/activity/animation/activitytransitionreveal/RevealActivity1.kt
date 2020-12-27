package vn.loitp.app.activity.animation.activitytransitionreveal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.tombayley.activitycircularreveal.CircularReveal
import com.views.setSafeOnClickListener
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
                500
        ).apply {
            revealColor = ContextCompat.getColor(
                    this@RevealActivity1,
                    R.color.green
            )
            requestCode = REQUEST_CODE
        }

        CircularReveal.presentActivity(builder)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val msg = data?.getStringExtra(RevealActivity2.KEY_DATA_RETURN)
            showShortInformation("onActivityResult REQUEST_CODE $REQUEST_CODE -> msg $msg")
        }
    }

}
