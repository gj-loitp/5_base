package vn.loitp.app.activity.customviews.layout.autolinearlayout

import android.os.Bundle

import com.core.base.BaseFontActivity

import loitp.basemaster.R

//read more
//https://github.com/AlbertGrobas/AutoLinearLayout?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=1852
class AutoLinearLayoutActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Java
        /*AutoLinearLayout l2 = new AutoLinearLayout(this);
        l2.setOrientation(AutoLinearLayout.VERTICAL);
        l2.setGravity(Gravity.CENTER);
        //add other views
        parent.addView(l2);*/
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_auto_linear_layout
    }
}
