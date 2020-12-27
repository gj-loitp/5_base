package vn.loitp.app.activity.animation.activitytransitionreveal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.IsSwipeActivity
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.tombayley.activitycircularreveal.CircularReveal
import vn.loitp.app.R

@LogTag("OtherActivity")
@IsFullScreen(false)
@IsSwipeActivity(true)
class RevealActivity2 : BaseFontActivity() {

    companion object {
        const val KEY_DATA_RETURN = "KEY_DATA_RETURN"
    }

    private var activityCircularReveal: CircularReveal? = null
    override fun setLayoutResourceId(): Int {
        return R.layout.activity_reveal_2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootView: View = findViewById(R.id.root_coord)

        activityCircularReveal = CircularReveal(rootView)
        activityCircularReveal?.onActivityCreate(intent)
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(KEY_DATA_RETURN, "data " + System.currentTimeMillis())
        setResult(Activity.RESULT_OK, intent)
        activityCircularReveal?.unRevealActivity(this)
    }

}
