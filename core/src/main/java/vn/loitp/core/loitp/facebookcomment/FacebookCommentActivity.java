package vn.loitp.core.loitp.facebookcomment;

import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.CookieManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import loitp.core.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LToast;
import vn.loitp.views.actionbar.LActionBar;

public class FacebookCommentActivity extends BaseFontActivity {
    private WebView mWebViewComments;
    private RelativeLayout mContainer;
    private ProgressBar progressBar;
    boolean isLoading;
    private WebView mWebviewPop;
    private String postUrl;
    private AdView adView;
    // the default number of comments should be visible
    // on page load.
    private static final int NUMBER_OF_COMMENTS = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
        String adUnitId = getIntent().getStringExtra(Constants.INSTANCE.getAD_UNIT_ID_BANNER());
        LLog.INSTANCE.d(TAG, "adUnitId " + adUnitId);
        LinearLayout lnAdview = (LinearLayout) findViewById(R.id.ln_adview);
        if (adUnitId == null || adUnitId.isEmpty()) {
            lnAdview.setVisibility(View.GONE);
        } else {
            adView = new AdView(activity);
            adView.setAdSize(AdSize.SMART_BANNER);
            adView.setAdUnitId(adUnitId);
            LUIUtil.createAdBanner(adView);
            lnAdview.addView(adView);
            lnAdview.requestLayout();
            //int navigationHeight = DisplayUtil.getNavigationBarHeight(activity);
            //LUIUtil.setMargins(lnAdview, 0, 0, 0, navigationHeight + navigationHeight / 3);
        }

        mWebViewComments = (WebView) findViewById(R.id.commentsView);
        mContainer = (RelativeLayout) findViewById(R.id.webview_frame);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        LUIUtil.setColorProgressBar(progressBar, ContextCompat.getColor(activity, R.color.colorPrimary));

        if (Constants.INSTANCE.getIS_DEBUG()) {
            postUrl = "https://www.androidhive.info/2016/06/android-firebase-integrate-analytics/";
        } else {
            postUrl = getIntent().getStringExtra(Constants.INSTANCE.getFACEBOOK_COMMENT_URL());
        }

        // finish the activity in case of empty url
        if (TextUtils.isEmpty(postUrl)) {
            LToast.INSTANCE.show(activity, "The web url shouldn't be empty");
            onBackPressed();
            return;
        }

        setLoading(true);
        loadComments();
    }

    private void setupActionBar() {
        LActionBar lActionBar = (LActionBar) findViewById(R.id.l_action_bar);
        lActionBar.setOnClickBack(new LActionBar.Callback() {
            @Override
            public void onClickBack() {
                onBackPressed();
            }

            @Override
            public void onClickMenu() {
                //LToast.show(activity, "onClickMenu");
            }
        });
        lActionBar.hideMenuIcon();
        lActionBar.hideBlurView();
        lActionBar.setTvTitle("Facebook Comment");
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
        return R.layout.activity_fb_cmt_core;
    }

    private void loadComments() {
        mWebViewComments.setWebViewClient(new UriWebViewClient());
        mWebViewComments.setWebChromeClient(new UriChromeClient());
        mWebViewComments.getSettings().setJavaScriptEnabled(true);
        mWebViewComments.getSettings().setAppCacheEnabled(true);
        mWebViewComments.getSettings().setDomStorageEnabled(true);
        mWebViewComments.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebViewComments.getSettings().setSupportMultipleWindows(true);
        mWebViewComments.getSettings().setSupportZoom(false);
        mWebViewComments.getSettings().setBuiltInZoomControls(false);
        CookieManager.getInstance().setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 21) {
            mWebViewComments.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(mWebViewComments, true);
        }

        // facebook comment widget including the article url
        String html = "<!doctype html> <html lang=\"en\"> <head></head> <body> " +
                "<div id=\"fb-root\"></div> <script>(function(d, s, id) { var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); js.id = id; js.src = \"//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6\"; fjs.parentNode.insertBefore(js, fjs); }(document, 'script', 'facebook-jssdk'));</script> " +
                "<div class=\"fb-comments\" data-href=\"" + postUrl + "\" " +
                "data-numposts=\"" + NUMBER_OF_COMMENTS + "\" data-order-by=\"reverse_time\">" +
                "</div> </body> </html>";

        mWebViewComments.loadDataWithBaseURL("http://www.nothing.com", html, "text/html", "UTF-8", null);
        mWebViewComments.setMinimumHeight(200);
    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
        if (isLoading) {
            LUIUtil.setProgressBarVisibility(progressBar, View.VISIBLE);
        } else {
            LUIUtil.setDelay(1000, new LUIUtil.DelayCallback() {
                @Override
                public void doAfter(int mls) {
                    LUIUtil.setProgressBarVisibility(progressBar, View.GONE);
                }
            });
        }
        invalidateOptionsMenu();
    }

    private class UriWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String host = Uri.parse(url).getHost();
            return !host.equals("m.facebook.com");
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            String host = Uri.parse(url).getHost();
            setLoading(false);
            if (url.contains("/plugins/close_popup.php?reload")) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        mContainer.removeView(mWebviewPop);
                        loadComments();
                    }
                }, 600);
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            setLoading(false);
        }
    }

    private class UriChromeClient extends WebChromeClient {

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            mWebviewPop = new WebView(getApplicationContext());
            mWebviewPop.setVerticalScrollBarEnabled(false);
            mWebviewPop.setHorizontalScrollBarEnabled(false);
            mWebviewPop.setWebViewClient(new UriWebViewClient());
            mWebviewPop.setWebChromeClient(this);
            mWebviewPop.getSettings().setJavaScriptEnabled(true);
            mWebviewPop.getSettings().setDomStorageEnabled(true);
            mWebviewPop.getSettings().setSupportZoom(false);
            mWebviewPop.getSettings().setBuiltInZoomControls(false);
            mWebviewPop.getSettings().setSupportMultipleWindows(true);
            mWebviewPop.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mContainer.addView(mWebviewPop);
            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(mWebviewPop);
            resultMsg.sendToTarget();
            return true;
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage cm) {
            LLog.INSTANCE.d(TAG, "onConsoleMessage: " + cm.message());
            return true;
        }

        @Override
        public void onCloseWindow(WebView window) {
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (adView != null) {
            adView.resume();
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}
