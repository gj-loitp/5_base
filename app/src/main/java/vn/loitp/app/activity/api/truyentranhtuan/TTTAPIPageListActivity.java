package vn.loitp.app.activity.api.truyentranhtuan;

import android.os.Bundle;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.utils.util.ToastUtils;
import com.views.progressloadingview.avloadingindicatorview.AVLoadingIndicatorView;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.api.truyentranhtuan.helper.pagelist.GetReadImgTask;

public class TTTAPIPageListActivity extends BaseFontActivity {
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
                LUIUtil.INSTANCE.printBeautyJson(imagesListOfOneChap, tv);
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
