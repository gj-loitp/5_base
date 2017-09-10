package vn.puresolutions.livestarv3.activity.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseActivity;
import vn.puresolutions.livestarv3.utilities.v3.LImageUtils;
import vn.puresolutions.livestarv3.view.LActionBar;

public class NotificationContentActivity extends BaseActivity {
    @BindView(R.id.labNotifyScreen_labHeader)
    LActionBar labHeader;
    @BindView(R.id.sdvNotifyScreen_Avatar)
    SimpleDraweeView sdvAvatar;
    @BindView(R.id.tvNotifyScreen_Title)
    TextView tvTitle;
    @BindView(R.id.tvNotifyScreen_Date)
    TextView tvDate;
    @BindView(R.id.tvNotifyScreen_Content)
    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }
    private void  init(){
        labHeader.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {

            }
        });

        Intent intent = getIntent();
        Notification notification = (Notification) intent.getExtras().getSerializable("Notification");
        LImageUtils.loadImage(sdvAvatar, notification.getAvatar());
        labHeader.setTvTitle(notification.getTitle());
        tvTitle.setText(notification.getTitleContent());
        tvContent.setText(notification.getContent());
        tvDate.setText(notification.getDate());
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
        return R.layout.activity_notification_content;
    }
}
