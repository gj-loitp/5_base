package vn.loitp.app.activity.customviews.imageview.continuousscrollableimageview

import android.os.Bundle
import com.core.base.BaseFontActivity
import vn.loitp.app.R

//https://github.com/Cutta/ContinuousScrollableImageView
class ContinuousScrollableImageViewActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        //Classic Way
//        image = new ContinuousScrollableImageView(this);
//        image.setResourceId(R.drawable.bg_sample);
//        image.setDirection(ContinuousScrollableImageView.DOWN);
//        image.setScaleType(ContinuousScrollableImageView.FIT_XY);
//        image.setDuration(3000);
//        rootLayout.addView(image);


//        //Builder Way
//        image = new ContinuousScrollableImageView.Builder(MenuMotionLayoutActivity.this)
//                .setResourceId(R.drawable.bg_sample)
//                .setDirection(ContinuousScrollableImageView.UP)
//                .setDuration(3000)
//                .setScaleType(ContinuousScrollableImageView.FIT_XY)
//                .build();
//        rootLayout.addView(image);
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_continuousscrollable_imageview
    }
}