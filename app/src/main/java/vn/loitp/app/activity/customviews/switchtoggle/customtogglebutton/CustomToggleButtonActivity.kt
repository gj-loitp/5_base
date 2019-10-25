package vn.loitp.app.activity.customviews.switchtoggle.customtogglebutton

import android.os.Bundle

import com.core.base.BaseFontActivity
import com.views.switchtoggle.customtogglebutton.LCustomToggle

import loitp.basemaster.R

class CustomToggleButtonActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val lCustomToggle1 = findViewById<LCustomToggle>(R.id.toggle_xml_1)
        //customToggle.addFirstIcon(R.drawable.l_ic_thumb_down_black_48dp);
        //customToggle.addSecondIcon(R.drawable.l_ic_thumb_up_black_48dp);
        //customToggle.setMagnification(9);
        //customToggle.setSlideBackgroundColor(Color.BLACK);
        //customToggle.setAnimationTime(700);
        //customToggle.setSlideColor(Color.GREEN);
        lCustomToggle1.setOnToggleClickListener(object : LCustomToggle.OnToggleClickListener {
            override fun onLefToggleEnabled(enabled: Boolean) {
                showShort("onLefToggleEnabled $enabled")
            }

            override fun onRightToggleEnabled(enabled: Boolean) {
                showShort("onRightToggleEnabled $enabled")
            }
        })

        val lCustomToggle2 = findViewById<LCustomToggle>(R.id.toggle_xml_2)
        lCustomToggle2.setOnToggleClickListener(object : LCustomToggle.OnToggleClickListener {
            override fun onLefToggleEnabled(enabled: Boolean) {
                showShort("onLefToggleEnabled $enabled")
            }

            override fun onRightToggleEnabled(enabled: Boolean) {
                showShort("onRightToggleEnabled $enabled")
            }
        })

        val lCustomToggle3 = findViewById<LCustomToggle>(R.id.toggle_xml_3)
        lCustomToggle3.setOnToggleClickListener(object : LCustomToggle.OnToggleClickListener {
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

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_custom_toggle_button
    }
}
