package vn.loitp.core.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import loitp.core.R;


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
            LUIUtil.transActivityFadeIn(activity);
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)));
            LUIUtil.transActivityFadeIn(activity);
        }
    }

    /*public static void moreApp(Activity activity) {
        String nameOfDeveloper = Const.NAME_OF_DEV;
        String uri = "https://play.google.com/store/apps/developer?id=" + nameOfDeveloper;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        activity.startActivity(intent);
        LUIUtil.transActivityLeftToRightAniamtion(activity);
    }*/

    public static void shareApp(Activity activity) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name));
            String sAux = "\nỨng dụng này rất bổ ích, thân mời bạn tải về cài đặt để trải nghiệm\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + activity.getPackageName();
            intent.putExtra(Intent.EXTRA_TEXT, sAux);
            activity.startActivity(Intent.createChooser(intent, "Vui lòng chọn"));
            LUIUtil.transActivityFadeIn(activity);
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
            activity.startActivity(Intent.createChooser(intent, "Vui lòng chọn"));
            LUIUtil.transActivityFadeIn(activity);
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
        LUIUtil.transActivityFadeIn(activity);
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
    /*public static void chatMessenger(Activity activity) {
        PackageManager packageManager = activity.getPackageManager();
        boolean isFBInstalled = false;
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.orca", 0).versionCode;
            if (versionCode >= 0) isFBInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            LLog.d(TAG, "packageManager com.facebook.orca: " + e.toString());
        }
        if (!isFBInstalled) {
            LToast.show(activity, "Ứng dụng Messenger chưa được cài đặt trên điện thoại của bạn", 0);
        } else {
            Uri uri = Uri.parse("fb-messenger://user/");
            uri = ContentUris.withAppendedId(uri, Long.valueOf("947139732073591"));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            try {
                activity.startActivity(intent);
                LUIUtil.transActivityLeftToRightAniamtion(activity);
            } catch (Exception e) {
                LToast.show(activity, "Không thể chạy Messenger, vui lòng thử lại sau", 0);
            }
        }
    }*/

    /*
   * send email support
   */
    /*public void sendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{context.getResources().getString(R.string.myEmailDev)});
        i.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.mail_subject_support));
        i.putExtra(Intent.EXTRA_TEXT, context.getResources().getString(R.string.mail_text_support));
        try {
            context.startActivity(Intent.createChooser(i, context.getString(R.string.send_mail_via)));
        } catch (android.content.ActivityNotFoundException ex) {

        }
    }*/
}
