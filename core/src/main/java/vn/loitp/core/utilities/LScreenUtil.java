package vn.loitp.core.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;

import vn.loitp.core.base.BaseActivity;

/**
 * File created on 8/31/2017.
 *
 * @author loitp
 */

public class LScreenUtil {
    private final static String TAG = LScreenUtil.class.getSimpleName();

    public int getStatusBarHeight(Context mContext) {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        //Log.d(TAG,"result bar height: "+result);
        return result;
    }

    public int getBottomBarHeight(Context mContext) {
        boolean hasMenuKey = ViewConfiguration.get(mContext).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            // Do whatever you need to do, this device has a navigation bar
            int result = 0;
            int resourceId = mContext.getResources().getIdentifier("design_bottom_navigation_height", "dimen", mContext.getPackageName());
            if (resourceId > 0) {
                result = mContext.getResources().getDimensionPixelSize(resourceId);
            }
            //Log.d(TAG,"result botbar height: "+result);
            return result;
        }

        return 0;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public static int getScreenHeightIncludeNavigationBar(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // include navigation bar
            display.getRealSize(outPoint);
        } else {
            // exclude navigation bar
            display.getSize(outPoint);
        }
        int mRealSizeHeight;
        if (outPoint.y > outPoint.x) {
            mRealSizeHeight = outPoint.y;
            //mRealSizeWidth = outPoint.x;
        } else {
            mRealSizeHeight = outPoint.x;
            //mRealSizeWidth = outPoint.y;
        }
        return mRealSizeHeight;
    }

    public static void showStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT < 16) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = activity.getWindow().getDecorView();
            // Show Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void hideStatusBar(Activity activity) {
        // Hide Status Bar
        if (Build.VERSION.SDK_INT < 16) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = activity.getWindow().getDecorView();
            // Hide Status Bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void toggleFullscreen(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags ^= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //attrs.flags ^= WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        //attrs.flags ^= WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        activity.getWindow().setAttributes(attrs);

        /*if (isFullScreen(activity)) {
            hideNavigationBar(activity);
        } else {
            showNavigationBar(activity);
        }*/
    }

    public static void toggleFullscreen(Activity activity, boolean isFullScreen) {
        if (isFullScreen) {
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            activity.getWindow()
                    .getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    public static void hideNavigationBar(Activity activity) {
        // set navigation bar status, remember to disable "setNavigationBarTintEnabled"
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        // This work only for android 4.4+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = activity.getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }
    }

    public static void showNavigationBar(Activity activity) {
        // set navigation bar status, remember to disable "setNavigationBarTintEnabled"
        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        // This work only for android 4.4+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = activity.getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }
    }

    public static void hideDefaultControls(@NonNull final Activity activity) {
        final Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final View decorView = window.getDecorView();
        if (decorView != null) {
            int uiOptions = decorView.getSystemUiVisibility();
            if (Build.VERSION.SDK_INT >= 14) {
                uiOptions |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            if (Build.VERSION.SDK_INT >= 16) {
                uiOptions |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            if (Build.VERSION.SDK_INT >= 19) {
                uiOptions |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public static void showDefaultControls(@NonNull final Activity activity) {
        final Window window = activity.getWindow();
        if (window == null) {
            return;
        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        final View decorView = window.getDecorView();
        if (decorView != null) {
            int uiOptions = decorView.getSystemUiVisibility();
            if (Build.VERSION.SDK_INT >= 14) {
                uiOptions &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
            }
            if (Build.VERSION.SDK_INT >= 16) {
                uiOptions &= ~View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            if (Build.VERSION.SDK_INT >= 19) {
                uiOptions &= ~View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * toggles fullscreen mode
     * <br/>
     * REQUIRE: android:configChanges="orientation|screenSize"
     * <pre>
     * sample:
     *     private boolean fullscreen;
     *     ................
     *     Activity activity = (Activity)context;
     *     toggleFullscreen(activity, !fullscreen);
     *     fullscreen = !fullscreen;
     * </pre>
     */
    /*public static void toggleFullscreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            // The UI options currently enabled are represented by a bitfield.
            // getSystemUiVisibility() gives us that bitfield.
            int uiOptions = activity.getWindow().getDecorView().getSystemUiVisibility();
            int newUiOptions = uiOptions;
            boolean isImmersiveModeEnabled =
                    ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
            if (isImmersiveModeEnabled) {
                LLog.d(TAG, "Turning immersive mode mode off. ");
            } else {
                LLog.d(TAG, "Turning immersive mode mode on.");
            }

            // Navigation bar hiding:  Backwards compatible to ICS.
            if (Build.VERSION.SDK_INT >= 14) {
                newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }

            // Status bar hiding: Backwards compatible to Jellybean
            if (Build.VERSION.SDK_INT >= 16) {
                newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
            }

            // Immersive mode: Backward compatible to KitKat.
            // Note that this flag doesn't do anything by itself, it only augments the behavior
            // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
            // all three flags are being toggled together.
            // Note that there are two immersive mode UI flags, one of which is referred to as "sticky".
            // Sticky immersive mode differs in that it makes the navigation and status bars
            // semi-transparent, and the UI flag does not get cleared when the user interacts with
            // the screen.
            if (Build.VERSION.SDK_INT >= 18) {
                newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            }
            activity.getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
        } else {
            // for android pre 11
            WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
            if (UizaScreenUtil.isFullScreen(activity)) {
                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            } else {
                attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            }
            activity.getWindow().setAttributes(attrs);
        }

        *//*try {
            // hide actionbar
            if (activity instanceof ActionBarActivity) {
                if (fullscreen) ((ActionBarActivity) activity).getSupportActionBar().hide();
                else ((ActionBarActivity) activity).getSupportActionBar().show();
            } else if (Build.VERSION.SDK_INT >= 11) {
                if (fullscreen) activity.getActionBar().hide();
                else activity.getActionBar().show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*//*

        // set landscape
        // if(fullscreen)  activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        // else activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
    }*/

    //rotate screen
    public static void setFullScreen(Context context, boolean isFullScreen) {
        if (isFullScreen) {
            ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        } else {
            ((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public static void replaceFragment(Activity activity, int containerFrameLayoutIdRes, Fragment fragment, boolean isAddToBackStack) {
        FragmentTransaction transaction = ((BaseActivity) activity).getSupportFragmentManager().beginTransaction();
        transaction.replace(containerFrameLayoutIdRes, fragment);
        if (isAddToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public static void addFragment(Activity activity, int containerFrameLayoutIdRes, Fragment fragment, boolean isAddToBackStack) {
        FragmentTransaction transaction = ((BaseActivity) activity).getSupportFragmentManager().beginTransaction();
        transaction.add(containerFrameLayoutIdRes, fragment);
        if (isAddToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public static boolean isFullScreen(Context context) {
        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return false;
            case Surface.ROTATION_90:
                return true;
            case Surface.ROTATION_180:
                return false;
            default:
                return true;
        }
    }

    //from 0 to 100
    public static void setBrightness(final Context context, int value) {
        if (context == null) {
            return;
        }

        boolean isCanWriteSystem = checkSystemWritePermission(context);
        LLog.d(TAG, "isCanWriteSystem " + isCanWriteSystem);

        if (!isCanWriteSystem) {
            LDialogUtil.showDialog1(context, "Thông báo", "Uiza cần bạn cần cấp quyền điều chỉnh độ sáng màn hình", "Cấp phép", new LDialogUtil.Callback1() {
                @Override
                public void onClick1() {
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("package:" + context.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    LActivityUtil.tranIn(context);
                }
            });
            return;
        }

        //constrain the value of brightness
        int brightness = 0;
        if (value < 0) {
            brightness = 0;
        } else if (value > 100) {
            brightness = 100;
        } else {
            brightness = value * 255 / 100;
        }
        LLog.d(TAG, "setBrightness " + brightness);

        try {
            //sets manual mode and brightnes 255
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);  //this will set the manual mode (set the automatic mode off)
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, brightness);  //this will set the brightness to maximum (255)

            //refreshes the screen
            int br = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
            WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
            lp.screenBrightness = (float) br / 255;
            ((Activity) context).getWindow().setAttributes(lp);

        } catch (Exception e) {
            LLog.e(TAG, "setBrightness " + e.toString());
        }
    }

    public static int getCurrentBrightness(Context context) {
        if (context == null) {
            return 0;
        }
        try {
            return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            LLog.e(TAG, "getCurrentBrightness" + e.toString());
            return 0;
        }
    }

    private static boolean checkSystemWritePermission(Context context) {
        boolean retVal = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(context);
            //LLog.d(TAG, "Can Write Settings: " + retVal);
            /*if (retVal) {
                LLog.d(TAG, "Write allowed");
            } else {
                LLog.d(TAG, "Write not allowed");
            }*/
        }
        return retVal;
    }
}
