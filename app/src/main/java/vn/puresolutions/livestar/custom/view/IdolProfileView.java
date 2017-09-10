package vn.puresolutions.livestar.custom.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.core.api.model.Broadcaster;
import vn.puresolutions.livestar.core.api.restclient.RestClient;
import vn.puresolutions.livestar.core.api.service.BroadcasterService;
import vn.puresolutions.livestar.custom.rxandroid.ApiSubscriber;
import vn.puresolutions.livestar.room.RoomCenter;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;

/**
 * Created by khanh on 8/29/16.
 */
public class IdolProfileView extends IRoomView {
    @BindView(R.id.idolProfileView_imgAvatar)
    SimpleDraweeView imgAvatar;
    @BindView(R.id.idolProfileView_tvName)
    TextView tvName;
    @BindView(R.id.idolProfileView_tvCurrentLevel)
    TextView tvCurrentLevel;
    @BindView(R.id.idolProfileView_tvNextLevel)
    TextView tvNextLevel;
    @BindView(R.id.idolProfileView_pgbLevel)
    RoundCornerProgressBar pgbLevel;
    @BindView(R.id.idolProfileView_tvHeart)
    TextView tvHeart;
    @BindView(R.id.idolProfileView_imgFacebook)
    ImageView imgFb;
    @BindView(R.id.idolProfileView_imgTwitter)
    ImageView imgTwitter;
    @BindView(R.id.idolProfileView_imgInstagram)
    ImageView imgInstagram;
    @BindView(R.id.idolProfileView_tvDescription)
    TextView tvDescription;

    public IdolProfileView(Context context) {
        this(context, null, 0);
    }

    public IdolProfileView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IdolProfileView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        addView(R.layout.view_idol_profile);
        ButterKnife.bind(this);
    }

    @Override
    public void loadData() {
        BroadcasterService service = RestClient.createService(BroadcasterService.class);
        subscribe(service.getBroadcaster(RoomCenter.getInstance().idolId), new ApiSubscriber<Broadcaster>() {
            @Override
            public void onSuccess(Broadcaster idol) {
                bindView(idol);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }
        });
    }

    public void bindView(Broadcaster idol) {
        LImageUtils.loadImage(imgAvatar, idol.getAvatar());
        tvName.setText(idol.getName());
        tvCurrentLevel.setText(String.valueOf(idol.getBroadcasterLevel()));
        tvNextLevel.setText(String.valueOf(idol.getBroadcasterLevel() + 1));
        pgbLevel.setProgress(idol.getLevelPercent());
        tvHeart.setText(getResources().getString(R.string.heartNumberFormat, idol.getHeart()));
        tvDescription.setText(idol.getDescription());

        if (!TextUtils.isEmpty(idol.getFacebookLink())) {
            imgFb.setTag(idol.getFacebookLink());
        } else {
            imgFb.setVisibility(GONE);
        }

        if (!TextUtils.isEmpty(idol.getTwitterLink())) {
            imgTwitter.setTag(idol.getTwitterLink());
        } else {
            imgTwitter.setVisibility(GONE);
        }

        if (!TextUtils.isEmpty(idol.getInstagramLink())) {
            imgInstagram.setTag(idol.getInstagramLink());
        } else {
            imgInstagram.setVisibility(GONE);
        }
    }

    @OnClick({R.id.idolProfileView_imgFacebook,
            R.id.idolProfileView_imgTwitter,
            R.id.idolProfileView_imgInstagram})
    public void onClick(View view) {
        String url = (String) view.getTag();
        if (!TextUtils.isEmpty(url)) {
            if (!url.contains("http")) {
                url = "https://" + url;
            }
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            getContext().startActivity(browserIntent);
        }
    }
}
