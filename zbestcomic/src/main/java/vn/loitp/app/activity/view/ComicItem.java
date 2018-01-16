package vn.loitp.app.activity.view;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.app.model.comic.Comic;
import vn.loitp.app.util.AppUtil;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.restapi.flickr.model.photosetgetlist.Photoset;
import vn.loitp.views.placeholderview.lib.placeholderview.annotations.Click;
import vn.loitp.views.placeholderview.lib.placeholderview.annotations.Layout;
import vn.loitp.views.placeholderview.lib.placeholderview.annotations.NonReusable;
import vn.loitp.views.placeholderview.lib.placeholderview.annotations.Resolve;
import vn.loitp.views.placeholderview.lib.placeholderview.annotations.View;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

//@Animate(Animation.SCALE_UP_ASC)
@NonReusable
@Layout(R.layout.item_comic)
public class ComicItem {

    @View(R.id.root_view)
    private RelativeLayout rootView;

    @View(R.id.iv_bkg)
    private ImageView ivBkg;

    @View(R.id.tv_title)
    private TextView tvTitle;

    @View(R.id.tv_date)
    private TextView tvDate;

    private Activity activity;
    private Comic comic;
    private int position;

    public ComicItem(Activity activity, Comic comic, int position, Callback callback) {
        this.activity = activity;
        this.comic = comic;
        this.position = position;
        this.callback = callback;
    }

    @Resolve
    private void onResolved() {
        rootView.setBackgroundColor(AppUtil.getColor(activity));
        //LImageUtil.load(activity, photoset.getPrimaryPhotoExtras().getUrlM(), imageView);
        tvTitle.setText(comic.getTitle());
        tvDate.setText(comic.getDate());
    }

    @Click(R.id.iv_bkg)
    private void onClick() {
        if (callback != null) {
            callback.onClick(comic, position);
        }
    }

    public interface Callback {
        public void onClick(Comic comic, int position);
    }

    private Callback callback;
}