package vn.loitp.up.a.anim.activityTransitionReveal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.IsSwipeActivity
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.tombayley.activitycircularreveal.CircularReveal
import vn.loitp.databinding.AReveal2Binding

@LogTag("OtherActivity")
@IsFullScreen(false)
@IsSwipeActivity(true)
class RevealActivity2 : BaseActivityFont() {

    companion object {
        const val KEY_DATA_RETURN = "KEY_DATA_RETURN"
    }

    private lateinit var binding: AReveal2Binding

    private var activityCircularReveal: CircularReveal? = null

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AReveal2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        activityCircularReveal = CircularReveal(binding.rootView)
        activityCircularReveal?.onActivityCreate(intent)
    }

    override fun onBaseBackPressed() {
//        super.onBaseBackPressed()
        val intent = Intent()
        intent.putExtra(KEY_DATA_RETURN, "data " + System.currentTimeMillis())
        setResult(Activity.RESULT_OK, intent)
        activityCircularReveal?.unRevealActivity(this)
    }
}
