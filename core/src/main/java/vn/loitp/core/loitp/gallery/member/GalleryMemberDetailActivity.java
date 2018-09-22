package vn.loitp.core.loitp.gallery.member;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.widget.ImageView;
import android.widget.TextView;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LImageUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.restapi.flickr.model.photosetgetphotos.Photo;

public class GalleryMemberDetailActivity extends BaseFontActivity {
    private ImageView imageView;
    private TextView tvTitle;
    public static final String PHOTO = "PHOTO";
    public static final String IV = "iv";
    public static final String TV = "tv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExist = false;
        imageView = (ImageView) findViewById(R.id.image_view);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        LUIUtil.setTextShadow(tvTitle);
        Photo photo = (Photo) getIntent().getSerializableExtra(PHOTO);
        if (photo != null) {
            LImageUtil.loadNoAmin(activity, photo.getUrlM(), imageView);
            tvTitle.setText(photo.getTitle());
        }
        ViewCompat.setTransitionName(imageView, IV);
        ViewCompat.setTransitionName(tvTitle, TV);
    }

    @Override
    protected boolean setFullScreen() {
        return true;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_member_detail;
    }
}
