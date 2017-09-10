/**
 * 
 */
package vn.puresolutions.livestarv3.utilities.old;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * @author Le Minh Khanh
 * @version 1.0.0
 * @since Mar 5, 2014
 */
public class ScreenUtils {

	public static void hideSystemBars(Activity activity) {
		View decorView = activity.getWindow().getDecorView();
		int config = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			config = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_FULLSCREEN
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			config = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_FULLSCREEN;
		}
		decorView.setSystemUiVisibility(config);
	}


	public static int getHeightScreen(Activity activity){
		DisplayMetrics displaymetrics = new DisplayMetrics();
		if (Build.VERSION.SDK_INT >= 17) {
			activity.getWindowManager().getDefaultDisplay().getRealMetrics(displaymetrics);
		} else {
			activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		}
		return displaymetrics.heightPixels;
	}

	public static int getWidthScreen(Activity activity){
		DisplayMetrics displaymetrics = new DisplayMetrics();
		if (Build.VERSION.SDK_INT >= 17) {
			activity.getWindowManager().getDefaultDisplay().getRealMetrics(displaymetrics);
		} else {
			activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		}
		return displaymetrics.widthPixels;
	}
	
	public static int getBottomActionBar(Activity context){
		View v = context.getActionBar().getCustomView();
		int[] loc = new int[2];
		v.getLocationOnScreen(loc);
		
		return loc[1] + v.getHeight();
	}

	public static int getStatusBarHeight(Activity activity) {
		int result = 0;
		int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = activity.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	// ==========================================
	// Inner/Anonymous Classes/Interfaces
	// ==========================================
	
	/**
	 * This method converts dp unit to equivalent pixels, depending on device density. 
	 * 
	 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on device density
	 */
	public static float convertDpToPixel(float dp, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi / 160f);
	    return px;
	}

	/**
	 * This method converts device specific pixels to density independent pixels.
	 * 
	 * @param px A value in px (pixels) unit. Which we need to convert into db
	 * @param context Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	public static float convertPixelsToDp(float px, Context context){
	    Resources resources = context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float dp = px / (metrics.densityDpi / 160f);
	    return dp;
	}
}
