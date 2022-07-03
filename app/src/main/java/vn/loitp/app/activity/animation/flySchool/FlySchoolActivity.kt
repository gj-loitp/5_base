package vn.loitp.app.activity.animation.flySchool

import android.os.Bundle
import com.loitpcore.animation.flySchool.ImgObject
import com.loitpcore.animation.flySchool.PATHS
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_animation_fly_school.*
import vn.loitp.app.R

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
