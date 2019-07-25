package vn.loitp.app.activity.customviews.keyword_hottags

import android.app.Activity

import androidx.core.content.ContextCompat

import com.views.hottagkeywords.AutoLabelUI
import com.views.hottagkeywords.AutoLabelUISettings
import com.views.placeholderview.annotations.Layout
import com.views.placeholderview.annotations.NonReusable
import com.views.placeholderview.annotations.Resolve
import com.views.placeholderview.annotations.View

import loitp.basemaster.R

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

//@Animate(Animation.CARD_LEFT_IN_ASC)
@NonReusable
@Layout(R.layout.item_pv_keyword_list)
class PVKeywordList(private val mActivity: Activity, private val strings: Array<String?>, private val callback: Callback) {
    private val TAG = javaClass.simpleName
    @View(R.id.label_view)
    private val mAutoLabel: AutoLabelUI? = null

    @Resolve
    private fun onResolved() {
        /*StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        mPlaceHolderView.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(staggeredGridLayoutManager);

        //int screenWidthInDp = (int) LScreenUtils.convertPixelsToDp((float) (LScreenUtils.getWidthScreen(mActivity) / 5), mActivity);
        //int widthViewHolder = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, screenWidthInDp, mActivity.getResources().getDisplayMetrics());

        for (int i = 0; i < strings.length; i++) {
            mPlaceHolderView.addView(new PVKeywordItem(mActivity, mPlaceHolderView, strings[i], i, callback));
        }*/


        //HOW TO USER AutoLabelUI?
        //check tutorial here https://github.com/DavidPizarro/AutoLabelUI?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=2436

        val autoLabelUISettings = AutoLabelUISettings.Builder()
                .withMaxLabels(strings.size)
                .withIconCross(R.drawable.cross)
                .withBackgroundResource(R.drawable.bt_tag)
                .withLabelsClickables(true)
                .withShowCross(false)
                .withTextColor(ContextCompat.getColor(mActivity, R.color.Black))
                .withTextSize(R.dimen.txt_14)
                .withLabelPadding(R.dimen.w_3)
                .build()

        mAutoLabel?.setSettings(autoLabelUISettings)
        mAutoLabel?.setOnLabelClickListener { lLabelView ->
            callback.onClick(lLabelView.text)
        }

        for (string in strings) {
            if (!string.isNullOrEmpty()) {
                mAutoLabel?.addLabel("  $string  ")
            }
        }
    }

    interface Callback {
        fun onClick(keyword: String)
    }
}