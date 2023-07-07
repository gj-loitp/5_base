package vn.loitp.up.a.anim.flySchool

import android.os.Bundle
import com.loitp.anim.flySchool.ImgObject
import com.loitp.anim.flySchool.PATHS
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AAnimationFlySchoolBinding

// https://github.com/cipherthinkers/shapeflyer?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=5370
@LogTag("FlySchoolActivity")
@IsFullScreen(false)
class FlySchoolActivity : BaseActivityFont() {
    private lateinit var binding: AAnimationFlySchoolBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAnimationFlySchoolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = FlySchoolActivity::class.java.simpleName
        }

        binding.floatingContainer.addPath(PATHS.S_INVERTED_BOTTOM_RIGHT)
        // mShapeFlyer.addPath(PATHS.S_BOTTOM_LEFT)
        binding.btPlay1.setOnClickListener {
            val imgObject = ImgObject()
            imgObject.avatar = "https://kenh14cdn.com/2016/photo-1-1472659093342.jpg"
            imgObject.url = "https://kenh14cdn.com/2016/photo-1-1472659093342.jpg"
            play1(imgObject)
        }
        binding.btPlay2.setOnClickListener {
            val imgObject = ImgObject()
            imgObject.avatar = "https://kenh14cdn.com/2016/photo-9-1472659093718.jpg"
            imgObject.url = "https://kenh14cdn.com/2016/photo-9-1472659093718.jpg"
            play2(imgObject)
        }
        binding.btPlay3.setOnClickListener {
            play3()
        }
    }

    override fun onDestroy() {
        binding.floatingContainer.release()
        super.onDestroy()
    }

    private fun play1(imgObject: ImgObject?) {
        // FlyBluePrint linearBluePrint = new FlyBluePrint(new FPoint(0, 0), FlyPath.getSimpleLinePath(new FPoint(1, 1)));
        // mShapeFlyer.startAnimation(R.drawable.logo, linearBluePrint);
        // mShapeFlyer.startAnimation(R.drawable.l_heart_icon);

        imgObject?.let {
            binding.floatingContainer.startAnimation(it, 0)
        }
    }

    private fun play2(imgObject: ImgObject?) {
        imgObject?.let {
            binding.floatingContainer.startAnimation(it, R.drawable.logo)
        }
    }

    private fun play3() {
        binding.floatingContainer.startAnimation(R.drawable.logo)
    }
}
