package vn.loitp.app.activity.demo.youtubeparser;

import android.os.Bundle;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.function.youtubeparser.ui.FrmYoutubeChannel;

public class YoutubeParserChannelActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrmYoutubeChannel frm = new FrmYoutubeChannel();
        Bundle bundle = new Bundle();
        bundle.putString(FrmYoutubeChannel.KEY_WATCHER_ACTIVITY, YoutubeParserActivity.class.getName());
        frm.setArguments(bundle);
        LScreenUtil.addFragment(activity, R.id.fl_container, frm, false);
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
    protected int setLayoutResourceId() {
        return R.layout.activity_youtube_parser_channel;
    }


}
