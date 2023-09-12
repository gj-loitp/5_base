package vn.loitp.up.a.anim.activityTransitionReveal

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
import vn.loitp.R
import vn.loitp.databinding.AReveal1Binding

@LogTag("MainActivity")
@IsFullScreen(false)
class RevealActivity1 : BaseActivityFont() {

    companion object {
        const val REQUEST_CODE = 69
    }

    private lateinit var binding: AReveal1Binding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AReveal1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = RevealActivity1::class.java.simpleName
        }
        binding.fab.setSafeOnClickListener {
            show(it)
        }
        binding.tv.setSafeOnClickListener {
            show(it)
        }
        binding.iv.setSafeOnClickListener {
            show(it)
        }
        binding.bt.setSafeOnClickListener {
            show(it)
        }
        binding.ib.setSafeOnClickListener {
            show(it)
        }
    }

    private fun show(view: View) {
        val builder = CircularReveal.Builder(
            activity = this,
            viewClicked = view,
            intent = Intent(this, RevealActivity2::class.java),
            duration = 1000
        ).apply {
            revealColor = ContextCompat.getColor(
                this@RevealActivity1, com.loitp.R.color.green
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
