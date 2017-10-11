package vn.loitp.app.activity.customviews.placeholderview.ex.androidexpandablenewsfeed;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import loitp.utils.util.ToastUtils;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Click;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Layout;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.Resolve;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.View;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.expand.ChildPosition;
import vn.loitp.app.activity.customviews.placeholderview._lib.placeholderview.annotations.expand.ParentPosition;
import vn.loitp.livestar.R;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

@Layout(R.layout.feed_item)
public class InfoView {

    @ParentPosition
    private int mParentPosition;

    @ChildPosition
    private int mChildPosition;

    @View(R.id.titleTxt)
    private TextView titleTxt;

    @View(R.id.captionTxt)
    private TextView captionTxt;

    @View(R.id.timeTxt)
    private TextView timeTxt;

    @View(R.id.imageView)
    private ImageView imageView;

    private Info mInfo;
    private Context mContext;

    public InfoView(Context context, Info info) {
        mContext = context;
        mInfo = info;
    }

    @Resolve
    private void onResolved() {
        titleTxt.setText(mInfo.getTitle());
        captionTxt.setText(mInfo.getCaption());
        timeTxt.setText(mInfo.getTime());
        Glide.with(mContext).load(mInfo.getImageUrl()).into(imageView);
    }

    @Click(R.id.titleTxt)
    private void onClickTitle() {
        ToastUtils.showShort(titleTxt.getText().toString());
    }

    @Click(R.id.captionTxt)
    private void onClickCaption() {
        ToastUtils.showShort(captionTxt.getText().toString());
    }

    @Click(R.id.imageView)
    private void onClickImage() {
        ToastUtils.showShort(mInfo.getImageUrl());
    }
}