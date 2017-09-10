package vn.puresolutions.livestarv3.activity.userprofile;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.view.LActionBar;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.LPref;

public class EditInfoNotifyActivity extends BaseActivity {
    @BindView(R.id.labEditNotify_Header)
    LActionBar labHeader;
    @BindView(R.id.sw_livestreaming)
    Switch swLivestreaming;
    @BindView(R.id.sw_gonna_livestreaming)
    Switch swGonnaLivestreaming;
    @BindView(R.id.sw_other_action)
    Switch swOtherAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
        initSwLivestreaming();
        initSwGonnaLivestreaming();
        initSwOtherAction();
    }

    private void init() {
        labHeader.setTvTitle(getString(R.string.edit_profilescreen_title_notify));
        labHeader.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {
                //do nothing
            }
        });

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
        return R.layout.activity_edit_info_notify;
    }

    private void initSwLivestreaming() {
        swLivestreaming.setChecked(LPref.isOnSwitchLivestreaming(activity));
        swLivestreaming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LPref.setSwitchLivestreaming(activity, isChecked);
            }
        });
    }

    private void initSwGonnaLivestreaming() {
        swGonnaLivestreaming.setChecked(LPref.isOnSwitchGonnaLivestreaming(activity));
        swGonnaLivestreaming.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LPref.setSwitchGonnaLivestreaming(activity, isChecked);
            }
        });
    }

    private void initSwOtherAction() {
        swOtherAction.setChecked(LPref.isOnSwitchOtherAction(activity));
        swOtherAction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                LPref.setSwitchOtherAction(activity, isChecked);
            }
        });
    }
}
