package vn.loitp.app.activity.demo.floatingview.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.views.LToast;
import com.views.floatingview.FloatingViewListener;
import com.views.floatingview.FloatingViewManager;

import vn.loitp.app.R;

public class FVCustomUZVideoService extends Service implements FloatingViewListener {
    public static final String EXTRA_CUTOUT_SAFE_AREA = "cutout_safe_area";

    //https://stackoverflow.com/questions/8725909/startforeground-does-not-show-my-notification
    //private  final int NOTIFICATION_ID = 908114;
    private final int NOTIFICATION_ID = 0;

    private final String PREF_KEY_LAST_POSITION_X = "last_position_x";
    private final String PREF_KEY_LAST_POSITION_Y = "last_position_y";
    private FloatingViewManager mFloatingViewManager;
    //private UZVideo uzVideo;
    private String entityIdDefaultVOD = "7699e10e-5ce3-4dab-a5ad-a615a711101e";
    private String entityIdDefaultLIVE = "1759f642-e062-4e88-b5f2-e3022bd03b57";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mFloatingViewManager != null) {
            return START_STICKY;
        }
        final DisplayMetrics metrics = new DisplayMetrics();
        final WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        final LayoutInflater inflater = LayoutInflater.from(this);
        final RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.fv_widget_video, null, false);

        ImageButton ib = relativeLayout.findViewById(R.id.ib);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LToast.show(getBaseContext(), "Click");
            }
        });

        //uzVideo = (UZVideo) relativeLayout.findViewById(R.id.uz_video);
        //uzVideo.playUrl("https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd");
        mFloatingViewManager = new FloatingViewManager(this, this);
        mFloatingViewManager.setFixedTrashIconImage(R.drawable.l_ic_trash_fixed);
        mFloatingViewManager.setActionTrashIconImage(R.drawable.l_ic_trash_action);
        mFloatingViewManager.setSafeInsetRect(intent.getParcelableExtra(EXTRA_CUTOUT_SAFE_AREA));
        // Setting Options(you can change options at any time)
        loadDynamicOptions();
        // Initial Setting Options (you can't change options after created.)
        final FloatingViewManager.Options options = loadOptions(metrics);
        mFloatingViewManager.addViewToWindow(relativeLayout, options);
        startForeground(NOTIFICATION_ID, createNotification(this));
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        destroy();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onFinishFloatingView() {
        stopSelf();
    }

    @Override
    public void onTouchFinished(boolean isFinishing, int x, int y) {
        if (!isFinishing) {
            // Save the last position
            final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            editor.putInt(PREF_KEY_LAST_POSITION_X, x);
            editor.putInt(PREF_KEY_LAST_POSITION_Y, y);
            editor.apply();
        }
    }

    private void destroy() {
        /*if (uzVideo != null) {
            uzVideo.onDestroy();
        }*/
        if (mFloatingViewManager != null) {
            mFloatingViewManager.removeAllViewToWindow();
            mFloatingViewManager = null;
        }
    }

    private static Notification createNotification(Context context) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.default_floatingview_channel_id));
        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(context.getString(R.string.mail_content_title));
        builder.setContentText(context.getString(R.string.content_text));
        builder.setOngoing(true);
        builder.setPriority(NotificationCompat.PRIORITY_MIN);
        builder.setCategory(NotificationCompat.CATEGORY_SERVICE);
        return builder.build();
    }

    private void loadDynamicOptions() {
        mFloatingViewManager.setDisplayMode(FloatingViewManager.DISPLAY_MODE_SHOW_ALWAYS);
        //mFloatingViewManager.setDisplayMode(FloatingViewManager.DISPLAY_MODE_HIDE_FULLSCREEN);
    }

    private FloatingViewManager.Options loadOptions(DisplayMetrics metrics) {
        final FloatingViewManager.Options options = new FloatingViewManager.Options();
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        // Shape
        options.shape = FloatingViewManager.SHAPE_CIRCLE;
        //options.shape = FloatingViewManager.SHAPE_RECTANGLE;

        // Margin
        options.overMargin = 0;

        // MoveDirection
        options.moveDirection = FloatingViewManager.MOVE_DIRECTION_DEFAULT;
        //options.moveDirection = FloatingViewManager.MOVE_DIRECTION_LEFT;
        //options.moveDirection = FloatingViewManager.MOVE_DIRECTION_RIGHT;
        //options.moveDirection = FloatingViewManager.MOVE_DIRECTION_NEAREST;
        //options.moveDirection = FloatingViewManager.MOVE_DIRECTION_NONE;
        //options.moveDirection = FloatingViewManager.MOVE_DIRECTION_THROWN;

        options.usePhysics = true;

        // Last position
        final boolean isUseLastPosition = true;
        if (isUseLastPosition) {
            final int defaultX = options.floatingViewX;
            final int defaultY = options.floatingViewY;
            options.floatingViewX = sharedPref.getInt(PREF_KEY_LAST_POSITION_X, defaultX);
            options.floatingViewY = sharedPref.getInt(PREF_KEY_LAST_POSITION_Y, defaultY);
        } else {
            // Init X/Y
            final int offset = (int) (48 + 8 * metrics.density);
            options.floatingViewX = metrics.widthPixels * 50 - offset;
            options.floatingViewY = metrics.heightPixels * 100 - offset;
        }
        // Initial Animation
        options.animateInitialMove = true;
        return options;
    }
}
