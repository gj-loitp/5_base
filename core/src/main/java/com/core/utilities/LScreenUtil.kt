package com.core.utilities

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import com.core.base.BaseActivity
import com.interfaces.Callback1
import com.utils.util.FragmentUtils

/**
 * File created on 7/17/2019.
 *
 * @author loitp
 */

class LScreenUtil {

    companion object {

        val screenWidth: Int
            get() = Resources.getSystem().displayMetrics.widthPixels

        val screenHeight: Int
            get() = Resources.getSystem().displayMetrics.heightPixels

        fun getStatusBarHeight(mContext: Context): Int {
            var result = 0
            val resourceId = mContext.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = mContext.resources.getDimensionPixelSize(resourceId)
            }
            //Log.d(TAG,"result bar height: "+result);
            return result
        }

        fun getBottomBarHeight(mContext: Context): Int {
            val hasMenuKey = ViewConfiguration.get(mContext).hasPermanentMenuKey()
            val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)

            if (!hasMenuKey && !hasBackKey) {
                // Do whatever you need to do, this device has a navigation bar
                var result = 0
                val resourceId = mContext.resources.getIdentifier("design_bottom_navigation_height", "dimen", mContext.packageName)
                if (resourceId > 0) {
                    result = mContext.resources.getDimensionPixelSize(resourceId)
                }
                //Log.d(TAG,"result botbar height: "+result);
                return result
            }

            return 0
        }

        fun getScreenHeightIncludeNavigationBar(context: Context): Int {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val outPoint = Point()
            if (Build.VERSION.SDK_INT >= 19) {
                // include navigation bar
                display.getRealSize(outPoint)
            } else {
                // exclude navigation bar
                display.getSize(outPoint)
            }
            val mRealSizeHeight: Int
            mRealSizeHeight = if (outPoint.y > outPoint.x) {
                outPoint.y
                //mRealSizeWidth = outPoint.x;
            } else {
                outPoint.x
                //mRealSizeWidth = outPoint.y;
            }
            return mRealSizeHeight
        }

        @SuppressLint("ObsoleteSdkInt")
        fun showStatusBar(activity: Activity) {
            if (Build.VERSION.SDK_INT < 16) {
                activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } else {
                val decorView = activity.window.decorView
                // Show Status Bar.
                val uiOptions = View.SYSTEM_UI_FLAG_VISIBLE
                decorView.systemUiVisibility = uiOptions
            }
        }

        @SuppressLint("ObsoleteSdkInt")
        fun hideStatusBar(activity: Activity) {
            // Hide Status Bar
            if (Build.VERSION.SDK_INT < 16) {
                activity.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } else {
                val decorView = activity.window.decorView
                // Hide Status Bar.
                val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                decorView.systemUiVisibility = uiOptions
            }
        }

        fun toggleFullscreen(activity: Activity) {
            val attrs = activity.window.attributes
            attrs.flags = attrs.flags xor WindowManager.LayoutParams.FLAG_FULLSCREEN
            //attrs.flags ^= WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            //attrs.flags ^= WindowManager.LayoutParams.FLAG_FULLSCREEN | WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            activity.window.attributes = attrs
            /*if (isFullScreen(activity)) {
                hideNavigationBar(activity);
            } else {
                showNavigationBar(activity);
            }*/
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        fun toggleFullscreen(activity: Activity, isFullScreen: Boolean) {
            if (isFullScreen) {
                activity.window
                        .decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            } else {
                activity.window
                        .decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        fun hideNavigationBar(activity: Activity) {
            // set navigation bar status, remember to disable "setNavigationBarTintEnabled"
            val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            // This work only for android 4.4+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.window.decorView.systemUiVisibility = flags

                // Code below is to handle presses of Volume up or Volume down.
                // Without this, after pressing volume buttons, the navigation bar will
                // show up and won't hide
                val decorView = activity.window.decorView
                decorView.setOnSystemUiVisibilityChangeListener { visibility ->
                    if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                        decorView.systemUiVisibility = flags
                    }
                }
            }
        }

        @TargetApi(Build.VERSION_CODES.KITKAT)
        fun showNavigationBar(activity: Activity) {
            // set navigation bar status, remember to disable "setNavigationBarTintEnabled"
            val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            // This work only for android 4.4+
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.window.decorView.systemUiVisibility = flags

                // Code below is to handle presses of Volume up or Volume down.
                // Without this, after pressing volume buttons, the navigation bar will
                // show up and won't hide
                val decorView = activity.window.decorView
                decorView.setOnSystemUiVisibilityChangeListener { visibility ->
                    if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                        decorView.systemUiVisibility = flags
                    }
                }
            }
        }

        @SuppressLint("ObsoleteSdkInt")
        fun hideDefaultControls(activity: Activity) {
            val window = activity.window ?: return
            window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            val decorView = window.decorView
            var uiOptions = decorView.systemUiVisibility
            if (Build.VERSION.SDK_INT >= 14) {
                uiOptions = uiOptions or View.SYSTEM_UI_FLAG_LOW_PROFILE
            }
            if (Build.VERSION.SDK_INT >= 16) {
                uiOptions = uiOptions or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }
            if (Build.VERSION.SDK_INT >= 19) {
                uiOptions = uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            }
            decorView.systemUiVisibility = uiOptions
        }

        @SuppressLint("ObsoleteSdkInt")
        fun showDefaultControls(activity: Activity) {
            val window = activity.window ?: return
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            val decorView = window.decorView
            var uiOptions = decorView.systemUiVisibility
            if (Build.VERSION.SDK_INT >= 14) {
                uiOptions = uiOptions and View.SYSTEM_UI_FLAG_LOW_PROFILE.inv()
            }
            if (Build.VERSION.SDK_INT >= 16) {
                uiOptions = uiOptions and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION.inv()
            }
            if (Build.VERSION.SDK_INT >= 19) {
                uiOptions = uiOptions and View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY.inv()
            }
            decorView.systemUiVisibility = uiOptions
        }

        //rotate screen
        fun setFullScreen(context: Context, isFullScreen: Boolean) {
            if (isFullScreen) {
                (context as Activity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            } else {
                (context as Activity).requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }

        fun replaceFragment(activity: Activity, containerFrameLayoutIdRes: Int, fragment: Fragment, isAddToBackStack: Boolean) {
            val transaction = (activity as BaseActivity).supportFragmentManager.beginTransaction()
            //transaction.setCustomAnimations(android.R.anim.fade_in, 0);
            transaction.replace(containerFrameLayoutIdRes, fragment)
            if (isAddToBackStack) {
                transaction.addToBackStack(null)
            }
            transaction.commit()
        }

        fun addFragment(activity: Activity, containerFrameLayoutIdRes: Int, fragment: Fragment, isAddToBackStack: Boolean) {
            val transaction = (activity as BaseActivity).supportFragmentManager.beginTransaction()
            //transaction.setCustomAnimations(android.R.anim.fade_in, 0);
            transaction.add(containerFrameLayoutIdRes, fragment)
            if (isAddToBackStack) {
                transaction.addToBackStack(null)
            }
            transaction.commit()
        }

        fun addFragment(activity: Activity, containerFrameLayoutIdRes: Int, fragment: Fragment, tag: String, isAddToBackStack: Boolean) {
            val transaction = (activity as BaseActivity).supportFragmentManager.beginTransaction()
            //transaction.setCustomAnimations(android.R.anim.fade_in, 0);
            transaction.add(containerFrameLayoutIdRes, fragment, tag)
            if (isAddToBackStack) {
                transaction.addToBackStack(null)
            }
            transaction.commit()
        }

        fun findFragmentByTag(activity: Activity, tag: String): Fragment? {
            return (activity as BaseActivity).supportFragmentManager.findFragmentByTag(tag)
        }

        fun removeFragmentByTag(activity: Activity, tag: String) {
            val fragment = findFragmentByTag(activity, tag)
            if (fragment != null) {
                val transaction = (activity as BaseActivity).supportFragmentManager.beginTransaction()
                //transaction.setCustomAnimations(0, android.R.anim.fade_out);
                transaction.remove(fragment).commit()
            }
        }

        fun removeAllFragments(activity: Activity) {
            FragmentUtils.removeAllFragments((activity as BaseActivity).supportFragmentManager)
        }

        fun isLandscape(context: Context): Boolean {
            val rotation = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.rotation
            return when (rotation) {
                Surface.ROTATION_0 -> false
                Surface.ROTATION_90 -> true
                Surface.ROTATION_180 -> false
                else -> true
            }
        }

        //from 0 to 100
        fun setBrightness(context: Context?, value: Int) {
            if (context == null) {
                return
            }

            val isCanWriteSystem = checkSystemWritePermission(context)

            if (!isCanWriteSystem) {
                LDialogUtil.showDialog1(context = context,
                        title = "Thông báo",
                        msg = "Ứng dụng cần bạn cần cấp quyền điều chỉnh độ sáng màn hình",
                        button1 = "Cấp phép",
                        callback1 = object : Callback1 {
                            @TargetApi(Build.VERSION_CODES.M)
                            override fun onClick1() {
                                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                                intent.data = Uri.parse("package:" + context.packageName)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                context.startActivity(intent)
                                LActivityUtil.tranIn(context)
                            }
                        })
                return
            }

            //constrain the value of brightness
            val brightness: Int = when {
                value < 0 -> 0
                value > 100 -> 100
                else -> value * 255 / 100
            }

            try {
                //sets manual parrallaxMode and brightnes 255
                Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL)  //this will set the manual parrallaxMode (set the automatic parrallaxMode off)
                Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness)  //this will set the brightness to maximum (255)

                //refreshes the screen
                val br = Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
                val lp = (context as Activity).window.attributes
                lp.screenBrightness = br.toFloat() / 255
                context.window.attributes = lp

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun getCurrentBrightness(context: Context?): Int {
            if (context == null) {
                return 0
            }
            return try {
                Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
            } catch (e: Exception) {
                e.printStackTrace()
                0
            }
        }

        fun checkSystemWritePermission(context: Context): Boolean {
            var retVal = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                retVal = Settings.System.canWrite(context)
                //LLog.d(TAG, "Can Write Settings: " + retVal);
                /*if (retVal) {
                    LLog.d(TAG, "Write allowed");
                } else {
                    LLog.d(TAG, "Write not allowed");
                }*/
            }
            return retVal
        }
    }
}
