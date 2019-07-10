package vn.loitp.app.activity.demo.youtubeparser;

import android.os.Bundle;

import com.core.base.BaseFontActivity;
import com.core.utilities.LScreenUtil;

import loitp.basemaster.R;
import vn.loitp.function.youtubeparser.ui.FrmYoutubeParser;

public class YoutubeParserActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String channelId = getIntent().getStringExtra(FrmYoutubeParser.KEY_CHANNEL_ID);
        FrmYoutubeParser frmYoutubeParser = new FrmYoutubeParser();
        Bundle bundle = new Bundle();
        bundle.putBoolean(FrmYoutubeParser.KEY_IS_ENGLISH_MSG, true);
        //bundle.putString(FrmYoutubeParser.KEY_CHANNEL_ID, "UCdDnufSiFrEvS7jqcKOkN0Q");//loitp channel
        bundle.putString(FrmYoutubeParser.KEY_CHANNEL_ID, channelId);
        frmYoutubeParser.setArguments(bundle);
        LScreenUtil.addFragment(activity, R.id.fl_container, frmYoutubeParser, false);
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
        return R.layout.activity_youtube_parser;
    }


}
