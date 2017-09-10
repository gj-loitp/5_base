package vn.puresolutions.livestarv3.activity.userprofile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.corev3.api.model.v3.login.UserProfile;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.view.LTextViewHorizontal;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.utilities.v3.LPref;
import vn.puresolutions.livestarv3.utilities.v3.VipUtil;

public class ProfileLevelActivity extends BaseActivity {
    @BindView(R.id.labProfileLevelScreen_Title)
    LActionBar lActionBar;
    @BindView(R.id.sdvProfileLevelScreen_Avatar)
    SimpleDraweeView sdvAvatar;
    @BindView(R.id.tvProfileLevelScreen_LevelProgress)
    TextView tvLevelProgress;
    @BindView(R.id.tvProfileLevelScreen_Name)
    TextView tvName;
    @BindView(R.id.lthProfileLevelScreen_Level)
    LTextViewHorizontal lthLevelInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        updateUI();
        lActionBar.setTvTitle(getString(R.string.guide_level_up));
        lActionBar.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {

            }
        });
    }

    private void updateUI() {
        UserProfile userProfile = LPref.getUser(this);
        LImageUtils.loadImage(sdvAvatar, (String) userProfile.getAvatarsPath().getW512h512());
        tvName.setText(userProfile.getName());
        int lv = (Integer) userProfile.getLevel().getLevel();
        lthLevelInfo.setImageSize(20);
        lthLevelInfo.setText(String.valueOf(lv));
        lthLevelInfo.setTextSize(18);
        lthLevelInfo.setBackground(R.drawable.bt_violet);
        if (VipUtil.getLevelIcon(userProfile.getLevel().getLevel()) != 0) {
            lthLevelInfo.setImage(VipUtil.getLevelIcon(userProfile.getLevel().getLevel()));
        }
        tvLevelProgress.setText(String.format(getString(R.string.profilescreen_level_progress), VipUtil.progressLevel(userProfile.getLevel().getLevel(), userProfile.getExp()), userProfile.getLevel().getLevel() + 1));
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_profile_level;
    }
}
