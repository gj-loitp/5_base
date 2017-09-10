package vn.puresolutions.livestar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseFragment;

/**
 * @author hoangphu
 * @since 11/27/16
 */

public class WebViewFragment extends BaseFragment {

    public static final String BUNDLE_KEY_URL = "url";
    private static final String BUNDLE_KEY_PAGE_RELOADED = "BUNDLE_KEY_PAGE_RELOADED";

    @BindView(R.id.webViewFragment_wvContent)
    WebView wvContent;
    @BindView(R.id.webViewFragment_pgbLoading)
    ProgressBar pgbLoading;

    private boolean reloaded;

    public static WebViewFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_URL, url);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        wvContent.getSettings().setJavaScriptEnabled(true);
        wvContent.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                pgbLoading.setVisibility(View.GONE);
                reloaded = true;
            }
        });

        String url = getArguments().getString(BUNDLE_KEY_URL);
        if (savedInstanceState == null) {
            wvContent.loadUrl(url);
        } else {
            wvContent.restoreState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        wvContent.saveState(outState);
    }
}