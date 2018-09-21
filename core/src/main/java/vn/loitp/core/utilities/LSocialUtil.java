package vn.loitp.core.utilities;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import loitp.core.R;
import vn.loitp.core.common.Constants;
import vn.loitp.core.loitp.facebookcomment.FacebookCommentActivity;


/**
 * File created on 11/14/2016.
 *
 * @author loitp
 */
public class LSocialUtil {
    private static String TAG = LSocialUtil.class.getSimpleName();

    /*
     * rate app
     * @param packageName: the packageName
     */
    public static void rateApp(Activity activity, String packageName) {
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
            LActivityUtil.tranIn(activity);
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
            LActivityUtil.tranIn(activity);
        }
    }

    public static void moreApp(Activity activity) {
        String nameOfDeveloper = "NgonTinh KangKang";
        String uri = "https://play.google.com/store/apps/developer?id=" + nameOfDeveloper;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        activity.startActivity(intent);
        LActivityUtil.tranIn(activity);
    }

    public static void shareApp(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name));
            String sAux = "\nỨng dụng này rất bổ ích, thân mời bạn tải về cài đặt để trải nghiệm\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + activity.getPackageName();
            intent.putExtra(Intent.EXTRA_TEXT, sAux);
            activity.startActivity(Intent.createChooser(intent, "Vui lòng chọn"));
            LActivityUtil.tranIn(activity);
        } catch (Exception e) {
            LLog.d(TAG, "shareApp: " + e.toString());
        }
    }

    public static void share(Activity activity, String msg) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name));
            //String sAux = "\nỨng dụng này rất bổ ích, thân mời bạn tải về cài đặt để trải nghiệm\n\n";
            //sAux = sAux + "https://play.google.com/store/apps/details?id=" + activity.getPackageName();
            intent.putExtra(Intent.EXTRA_TEXT, msg);
            activity.startActivity(Intent.createChooser(intent, "Share via"));
            LActivityUtil.tranIn(activity);
        } catch (Exception e) {
            LLog.d(TAG, "shareApp: " + e.toString());
        }
    }

    //like fanpage
    public static void likeFacebookFanpage(Activity activity) {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(activity);
        facebookIntent.setData(Uri.parse(facebookUrl));
        activity.startActivity(facebookIntent);
        LActivityUtil.tranIn(activity);
    }

    /*
    get url fb fanpage
     */
    private static String getFacebookPageURL(Context context) {
        String FACEBOOK_URL = "https://www.facebook.com/hoidammedocsach";
        String FACEBOOK_PAGE_ID = "hoidammedocsach";
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
            } else {
                return "fb://page/" + FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL;
        }
    }

    /*
    chat with fanpage Thugiannao
     */
    public static void chatMessenger(Activity activity) {
        PackageManager packageManager = activity.getPackageManager();
        boolean isFBInstalled = false;
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.orca", 0).versionCode;
            if (versionCode >= 0) isFBInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            LLog.d(TAG, "packageManager com.facebook.orca: " + e.toString());
        }
        if (!isFBInstalled) {
            LDialogUtil.showDialog1(activity, activity.getString(R.string.err), activity.getString(R.string.cannot_find_messenger_app), activity.getString(R.string.ok), null);
        } else {
            Uri uri = Uri.parse("fb-messenger://user/");
            uri = ContentUris.withAppendedId(uri, Long.valueOf("947139732073591"));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            try {
                activity.startActivity(intent);
                LActivityUtil.tranIn(activity);
            } catch (Exception e) {
                LDialogUtil.showDialog1(activity, activity.getString(R.string.err), activity.getString(R.string.cannot_find_messenger_app), activity.getString(R.string.ok), null);

            }
        }
    }

    /*
     * send email support
     */
    public static void sendEmail(Context context) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto: www.muathu@gmail.com"));
        context.startActivity(Intent.createChooser(emailIntent, "Send feedback"));
    }

    public static void openBrowserPolicy(Context context) {
        openUrlInBrowser(context, Constants.URL_POLICY);
    }

    public static void openUrlInBrowser(Context context, String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
            LActivityUtil.tranIn(context);
        }
    }

    public static void openFacebookComment(Context context, String url) {
        Intent intent = new Intent(context, FacebookCommentActivity.class);
        intent.putExtra(Constants.FACEBOOK_COMMENT_URL, url);
        context.startActivity(intent);
        LActivityUtil.tranIn(context);
    }

    public static void openFacebookComment(Context context, String url, String adUnitId) {
        Intent intent = new Intent(context, FacebookCommentActivity.class);
        intent.putExtra(Constants.FACEBOOK_COMMENT_URL, url);
        intent.putExtra(Constants.AD_UNIT_ID_BANNER, adUnitId);
        context.startActivity(intent);
        LActivityUtil.tranIn(context);
    }
}
