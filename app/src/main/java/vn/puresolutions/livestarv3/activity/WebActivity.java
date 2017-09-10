package vn.puresolutions.livestarv3.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestar.common.Constants;
import vn.puresolutions.livestarv3.base.BaseActivity;

public class WebActivity extends BaseActivity {
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.webview)
    WebView wvContent;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        wvContent.getSettings().setJavaScriptEnabled(true);
        wvContent.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                avi.smoothToHide();
                /*reloaded = true;*/
            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                final Uri uri = Uri.parse(url);
                return handleUri(uri);
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                final Uri uri = request.getUrl();
                return handleUri(uri);
            }

            private boolean handleUri(final Uri uri) {
                avi.smoothToShow();
                return false;
            }
        });
        String url = getIntent().getStringExtra(Constants.URL);
        wvContent.loadUrl(url);
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
        return R.layout.activity_webview;
    }


}
