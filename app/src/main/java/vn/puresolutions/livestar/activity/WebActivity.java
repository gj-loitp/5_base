package vn.puresolutions.livestar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseActivity;

/**
 * Created by khanh on 3/19/17.
 */

public class WebActivity extends BaseActivity {
    public static final String BUNDLE_KEY_URL = "url";
    public static final String BUNDLE_KEY_TITLE = "title";

    @BindView(R.id.webViewActivity_wvContent)
    WebView wvContent;
    @BindView(R.id.webViewActivity_pgbLoading)
    ProgressBar pgbLoading;
    @BindView(R.id.purchaseActivity_toolbar)
    Toolbar toolbar;
    @BindView(R.id.purchaseActivity_tvTitle)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        wvContent.getSettings().setJavaScriptEnabled(true);
        wvContent.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                pgbLoading.setVisibility(View.GONE);
            }
        });

        String url = getIntent().getStringExtra(BUNDLE_KEY_URL);
        wvContent.loadUrl(url);

        String title = getIntent().getStringExtra(BUNDLE_KEY_TITLE);
        tvTitle.setText(title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
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
        return R.layout.activity_web;
    }
}
