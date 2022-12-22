package vn.loitp.activity.anim.flySchool

import android.os.Bundle
import com.loitp.animation.flySchool.ImgObject
import com.loitp.animation.flySchool.PATHS
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_animation_fly_school.*
import vn.loitp.R

// https://github.com/cipherthinkers/shapeflyer?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=5370
@LogTag("FlySchoolActivity")
@IsFullScreen(false)
class FlySchoolActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_fly_school
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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = FlySchoolActivity::class.java.simpleName
        }

        floatingContainer.addPath(PATHS.S_INVERTED_BOTTOM_RIGHT)
        // mShapeFlyer.addPath(PATHS.S_BOTTOM_LEFT)
        btPlay1.setOnClickListener {
            val imgObject = ImgObject()
            imgObject.avatar = "https://kenh14cdn.com/2016/photo-1-1472659093342.jpg"
            imgObject.url = "https://kenh14cdn.com/2016/photo-1-1472659093342.jpg"
            play1(imgObject)
        }
        btPlay2.setOnClickListener {
            val imgObject = ImgObject()
            imgObject.avatar = "https://kenh14cdn.com/2016/photo-9-1472659093718.jpg"
            imgObject.url = "https://kenh14cdn.com/2016/photo-9-1472659093718.jpg"
            play2(imgObject)
        }
        btPlay3.setOnClickListener {
            play3()
        }
    }

    override fun onDestroy() {
        floatingContainer?.release()
        super.onDestroy()
    }

    private fun play1(imgObject: ImgObject?) {
        // FlyBluePrint linearBluePrint = new FlyBluePrint(new FPoint(0, 0), FlyPath.getSimpleLinePath(new FPoint(1, 1)));
        // mShapeFlyer.startAnimation(R.drawable.logo, linearBluePrint);
        // mShapeFlyer.startAnimation(R.drawable.l_heart_icon);

        imgObject?.let {
            floatingContainer?.startAnimation(it, 0)
        }
    }

    private fun play2(imgObject: ImgObject?) {
        imgObject?.let {
            floatingContainer.startAnimation(it, R.drawable.logo)
        }
    }

    private fun play3() {
        floatingContainer?.startAnimation(R.drawable.logo)
    }
}
