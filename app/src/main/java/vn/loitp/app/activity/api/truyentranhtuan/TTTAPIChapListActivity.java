package vn.loitp.app.activity.api.truyentranhtuan;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import vn.loitp.app.activity.api.truyentranhtuan.helper.chaplist.GetChapTask;
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.TTTChap;
import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class TTTAPIChapListActivity extends BaseActivity {
    private TextView tvTitle;
    private TextView tv;
    private AVLoadingIndicatorView avi;
    private Button btSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btSelect = (Button) findViewById(R.id.bt_select);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tv = (TextView) findViewById(R.id.tv);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        String urlComic = "http://truyentranhtuan.com/one-piece/";
        new GetChapTask(activity, urlComic, new GetChapTask.Callback() {
            @Override
            public void onSuccess(TTTChap tttChap) {
                LUIUtil.printBeautyJson(tttChap, tv);
                avi.smoothToHide();
                tvTitle.setText("Chap truyện One Piece - size: " + tttChap.getChaps().getChap().size());
            }

            @Override
            public void onError() {
                ToastUtils.showShort("onError");
            }
        }).execute();
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
        return R.layout.activity_api_ttt_chap_list;
    }
}
