package vn.loitp.app.activity.api.truyentranhtuan;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import vn.loitp.app.activity.api.truyentranhtuan.helper.pagelist.GetReadImgTask;
import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class TTTAPIPageListActivity extends BaseActivity {
    private TextView tv;
    private TextView tvTitle;
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        String currentLink = "http://truyentranhtuan.com/one-piece-chuong-69/";
        new GetReadImgTask(currentLink, avi, new GetReadImgTask.Callback() {
            @Override
            public void onSuccess(List<String> imagesListOfOneChap) {
                LUIUtil.printBeautyJson(imagesListOfOneChap, tv);
                tvTitle.setText("Danh sách page trong chap 69 - size: " + imagesListOfOneChap.size());
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
        return R.layout.activity_api_ttt_page_list;
    }
}
