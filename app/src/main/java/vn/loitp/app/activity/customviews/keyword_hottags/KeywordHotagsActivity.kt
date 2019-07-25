package vn.loitp.app.activity.customviews.keyword_hottags

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_keyword_hottags.*
import loitp.basemaster.R

class KeywordHotagsActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LUIUtil.setPullLikeIOSVertical(placeHolderView)
        val max = 50
        val strings = arrayOfNulls<String>(max)
        for (i in 0 until max) {
            strings[i] = "$i loitp"
        }

        placeHolderView.addView(PVKeywordList(activity, strings, object : PVKeywordList.Callback {
            override fun onClick(keyword: String) {
                LLog.d(TAG, "onClick $keyword");
                showShort("onClick $keyword")
            }
        }))
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_keyword_hottags
    }
}
