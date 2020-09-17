package vn.loitp.app.activity.customviews.switchtoggle.customtogglebutton

import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.views.switchtoggle.customtogglebutton.LCustomToggle
import kotlinx.android.synthetic.main.activity_switch_custom_toggle_button.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_switch_custom_toggle_button)
class CustomToggleButtonActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //customToggle.addFirstIcon(R.drawable.l_ic_thumb_down_black_48dp);
        //customToggle.addSecondIcon(R.drawable.l_ic_thumb_up_black_48dp);
        //customToggle.setMagnification(9);
        //customToggle.setSlideBackgroundColor(Color.BLACK);
        //customToggle.setAnimationTime(700);
        //customToggle.setSlideColor(Color.GREEN);
        toggleXml1.setOnToggleClickListener(object : LCustomToggle.OnToggleClickListener {
            override fun onLefToggleEnabled(enabled: Boolean) {
                showShort("onLefToggleEnabled $enabled")
            }

            override fun onRightToggleEnabled(enabled: Boolean) {
                showShort("onRightToggleEnabled $enabled")
            }
        })

        toggleXml2.setOnToggleClickListener(object : LCustomToggle.OnToggleClickListener {
            override fun onLefToggleEnabled(enabled: Boolean) {
                showShort("onLefToggleEnabled $enabled")
            }

            override fun onRightToggleEnabled(enabled: Boolean) {
                showShort("onRightToggleEnabled $enabled")
            }
        })

        toggleXml3.setOnToggleClickListener(object : LCustomToggle.OnToggleClickListener {
            override fun onLefToggleEnabled(enabled: Boolean) {
                showShort("onLefToggleEnabled $enabled")
            }

            override fun onRightToggleEnabled(enabled: Boolean) {
                showShort("onRightToggleEnabled $enabled")
            }
        })
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

}
