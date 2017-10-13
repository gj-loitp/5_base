package vn.loitp.app.activity.customviews.keyword_hottags;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.PlaceHolderView;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Click;
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
@Layout(R.layout.pv_keyword_item)
public class PVKeywordItem {
    @View(R.id.tv_keyword)
    protected TextView tvKeyword;

    @View(R.id.ll_main)
    protected LinearLayout ll_main;

    private Context mContext;
    private PlaceHolderView mPlaceHolderView;
    private int position;
    private String string;

    public PVKeywordItem(Context context, PlaceHolderView placeHolderView, String string, int position, Callback callback) {
        this.mContext = context;
        this.mPlaceHolderView = placeHolderView;
        this.string = string;
        this.position = position;
        this.callback = callback;
    }

    @Resolve
    private void onResolved() {
        if (string.length() < 5) {
            tvKeyword.setText("          " + string + "          ");//space is important here
        } else {
            tvKeyword.setText(string);
        }
    }

    @Click(R.id.ll_main)
    private void onClick() {
        if (callback != null) {
            callback.onClick(string, position);
        }
    }

    public interface Callback {
        public void onClick(String keyword, int position);
    }

    private Callback callback;
}