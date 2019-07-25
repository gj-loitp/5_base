package vn.loitp.app.activity.customviews.keyword_hottags

import android.content.Context
import android.widget.LinearLayout
import android.widget.TextView
import com.views.placeholderview.PlaceHolderView
import com.views.placeholderview.annotations.*
import loitp.basemaster.R

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

//@Animate(Animation.CARD_LEFT_IN_ASC)
@NonReusable
@Layout(R.layout.item_pv_keyword)
class PVKeywordItem(private val mContext: Context, private val mPlaceHolderView: PlaceHolderView, private val string: String, private val position: Int, private val callback: Callback?) {
    @View(R.id.tv_keyword)
    private var tvKeyword: TextView? = null

    @View(R.id.ll_main)
    private var llMain: LinearLayout? = null

    @Resolve
    private fun onResolved() {
        if (string.length < 5) {
            tvKeyword?.text = "          $string          "//space is important here
        } else {
            tvKeyword?.text = string
        }
    }

    @Click(R.id.ll_main)
    private fun onClick() {
        callback?.onClick(string, position)
    }

    interface Callback {
        fun onClick(keyword: String, position: Int)
    }
}