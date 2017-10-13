package vn.loitp.app.activity.customviews.keyword_hottags;

import android.app.Activity;
import android.support.v4.content.ContextCompat;

import vn.loitp.app.activity.customviews.keyword_hottags._lib.keyword.AutoLabelUI;
import vn.loitp.app.activity.customviews.keyword_hottags._lib.keyword.AutoLabelUISettings;
import vn.loitp.app.activity.customviews.keyword_hottags._lib.keyword.Label;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Layout;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.NonReusable;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Resolve;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.View;
import vn.loitp.livestar.R;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

//@Animate(Animation.CARD_LEFT_IN_ASC)
@NonReusable
@Layout(R.layout.pv_keyword_list)
public class PVKeywordList {
    private final String TAG = getClass().getSimpleName();
    @View(R.id.label_view)
    private AutoLabelUI mAutoLabel;


    private Activity mActivity;
    private String[] strings;

    private Callback callback;

    public PVKeywordList(Activity activity, String[] strings, Callback callback) {
        this.mActivity = activity;
        this.strings = strings;
        this.callback = callback;
    }

    @Resolve
    private void onResolved() {
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

        AutoLabelUISettings autoLabelUISettings = new AutoLabelUISettings.Builder()
                .withMaxLabels(strings.length)
                .withIconCross(R.drawable.cross)
                .withBackgroundResource(R.drawable.bt_tag)
                .withLabelsClickables(true)
                .withShowCross(false)
                .withTextColor(ContextCompat.getColor(mActivity, R.color.Black))
                .withTextSize(R.dimen.txt_14)
                .withLabelPadding(R.dimen.w_3)
                .build();

        mAutoLabel.setSettings(autoLabelUISettings);
        mAutoLabel.setOnLabelClickListener(new AutoLabelUI.OnLabelClickListener() {
            @Override
            public void onClickLabel(Label labelClicked) {
                if (callback != null) {
                    callback.onClick(labelClicked.getText());
                }
            }
        });

        for (int i = 0; i < strings.length; i++) {
            if (!strings[i].isEmpty()) {
                mAutoLabel.addLabel("  " + strings[i] + "  ");
            }
        }
    }

    public interface Callback {
        public void onClick(String keyword);
    }
}