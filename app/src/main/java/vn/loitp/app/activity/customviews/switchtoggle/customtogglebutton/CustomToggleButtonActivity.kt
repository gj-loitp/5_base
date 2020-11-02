package vn.loitp.app.activity.customviews.switchtoggle.customtogglebutton

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.switchtoggle.customtogglebutton.LCustomToggle
import kotlinx.android.synthetic.main.activity_switch_custom_toggle_button.*
import vn.loitp.app.R

@LogTag("CustomToggleButtonActivity")
@IsFullScreen(false)
class CustomToggleButtonActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_switch_custom_toggle_button
    }

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
                showShortInformation("onLefToggleEnabled $enabled")
            }

            override fun onRightToggleEnabled(enabled: Boolean) {
                showShortInformation("onRightToggleEnabled $enabled")
            }
        })

        toggleXml2.setOnToggleClickListener(object : LCustomToggle.OnToggleClickListener {
            override fun onLefToggleEnabled(enabled: Boolean) {
                showShortInformation("onLefToggleEnabled $enabled")
            }

            override fun onRightToggleEnabled(enabled: Boolean) {
                showShortInformation("onRightToggleEnabled $enabled")
            }
        })

        toggleXml3.setOnToggleClickListener(object : LCustomToggle.OnToggleClickListener {
            override fun onLefToggleEnabled(enabled: Boolean) {
                showShortInformation("onLefToggleEnabled $enabled")
            }

            override fun onRightToggleEnabled(enabled: Boolean) {
                showShortInformation("onRightToggleEnabled $enabled")
            }
        })
    }

}
