package vn.loitp.views.uizavideo.view.floatview;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import loitp.core.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.views.LToast;

/**
 * Created by LENOVO on 3/27/2018.
 */

public class FloatingUizaVideoService extends Service {
    private final String TAG = getClass().getSimpleName();
    private WindowManager mWindowManager;
    private View mFloatingView;

    public FloatingUizaVideoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFloatingView = LayoutInflater.from(this).inflate(R.layout.layout_floating_uiza_video, null);
        //Add the view to the window.
        /*final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);*/

        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        }

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                LAYOUT_FLAG,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        //Specify the view position
        //params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        //params.x = 0;
        //params.y = 100;

        //Add the view to the window
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mFloatingView, params);

        /*FrameLayout fl = (FrameLayout) mFloatingView.findViewById(R.id.fl);
        final FrmUizaIMAVideo frmUizaIMAVideo = new FrmUizaIMAVideo();
        LScreenUtil.replaceFragment(getBaseContext(), fl.getId(), frmUizaIMAVideo, false);
        frmUizaIMAVideo.setFragmentCallback(new BaseFragment.FragmentCallback() {
            @Override
            public void onViewCreated() {
                String linkPlay = getString(loitp.core.R.string.url_dash);
                String urlIMAAd = getString(loitp.core.R.string.ad_tag_url);
                String urlThumnailsPreviewSeekbar = getString(loitp.core.R.string.url_thumbnails);
                frmUizaIMAVideo.initData(linkPlay, urlIMAAd, urlThumnailsPreviewSeekbar);
            }
        });*/
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingView != null) mWindowManager.removeView(mFloatingView);
    }

    private void openApp() {
        //Open the application  click.
        LToast.show(this, "openApp");
        /*Intent intent = new Intent(FloatingViewVideoService.this, FloatingWidgetVideoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
        stopSelf();
    }
}