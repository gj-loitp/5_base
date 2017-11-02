package vn.loitp.app.activity.api.truyentranhtuan;

import android.app.Activity;
import android.os.Bundle;

import vn.loitp.app.base.BaseActivity;
import vn.loitp.livestar.R;

public class TruyenTranhTuanAPIActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return R.layout.activity_api_truyentranhtuan;
    }


    /*private void testGetPoster() {
        LSService service = RestClient.createService(LSService.class);
        subscribe(service.getPoster(3), new ApiSubscriber<GetPoster[]>() {
            @Override
            public void onSuccess(GetPoster[] result) {
                //LLog.d(TAG, "onSuccess " + LSApplication.getInstance().getGson().toJson(result));
                //tv.setText(LSApplication.getInstance().getGson().toJson(result));
                LUIUtil.printBeautyJson(result, tv);
            }

            @Override
            public void onFail(Throwable e) {
                handleException(e);
            }

            @Override
            public void onFinally(boolean success) {
                LLog.d(TAG, "onFinally success " + success);
            }
        });
    }*/
}
