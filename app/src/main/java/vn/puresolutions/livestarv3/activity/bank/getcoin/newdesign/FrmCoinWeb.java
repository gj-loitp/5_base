package vn.puresolutions.livestarv3.activity.bank.getcoin.newdesign;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.base.BaseFragment;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmCoinWeb extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    @BindView(R.id.wvfrmCoin_AddCoin)
    WebView wvContent;
    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_coin_web, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
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
        //TODO hardcode
        //String url = String.format(getString(R.string.ls_web_payment_url_v3),"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6ODA3NDksImVtYWlsIjoiZGd2dGFuaEBnbWFpbC5jb20iLCJuYW1lIjoiZGd2dGFuaCIsInZpcCI6MiwiZXhwIjoxNTAzNDU5MTkzfQ.8aW4kysfyPkU3Z-dFwEH_MZmDelivgLa7Kp-A8VBTac");
        String url = "http://www.livestar.vn/paymentforapp?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6ODA3NDksImVtYWlsIjoiZGd2dGFuaEBnbWFpbC5jb20iLCJuYW1lIjoiZGd2dGFuaCIsInZpcCI6MiwiZXhwIjoxNTAzNDU5MTkzfQ.8aW4kysfyPkU3Z-dFwEH_MZmDelivgLa7Kp-A8VBTac";
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

