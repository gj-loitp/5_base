package vn.loitp.app.activity.customviews.textview.colortextview

import android.os.Bundle

import com.core.base.BaseFontActivity

import vn.loitp.app.R

class ColorTextViewActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //can set color in xml or java code
        /*((ColorTextView) findViewById(R.id.ctv_text))
                .findAndSetStrColor("after", "#ff8000")
                .findAndSetStrColor("and", "#008888");*/
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_color_textview
    }

}
