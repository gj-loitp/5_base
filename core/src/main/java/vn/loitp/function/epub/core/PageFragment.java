package vn.loitp.function.epub.core;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import loitp.core.R;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPref;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.function.epub.BookSection;

/**
 * Created by loitp on 08.09.2016.
 */
public class PageFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private TextView tvLoading;
    private WebView webView;

    @Override
    protected int setLayoutResourceId() {
        return R.layout.frm_epub_reader;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (WebView) view.findViewById(R.id.wv);
        tvLoading = (TextView) view.findViewById(R.id.tv_loading);
    }

    public void setData(int position, BookSection bookSection) {
        LLog.d(TAG, "setData position " + position);
        if (bookSection != null) {
            setFragmentView(bookSection.getSectionContent(), "text/html", "UTF-8");
        }
    }

    public void setFragmentView(String data, String mimeType, String encoding) {
        if (getView() == null) {
            LLog.d(TAG, "getView() == null -> return");
            return;
        }
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        //webView.loadDataWithBaseURL(null, data, mimeType, encoding, null);
        webView.loadDataWithBaseURL("file:///android_asset/",
                getStyledFont(data),
                mimeType, encoding, "about:blank");
        int size = LPref.getTextSizeEpub(getActivity());
        updateUIWevViewSize(webView, size);
        tvLoading.setVisibility(View.GONE);
    }

    private String getStyledFont(String html) {
        boolean addBodyStart = !html.toLowerCase().contains("<body>");
        boolean addBodyEnd = !html.toLowerCase().contains("</body");
        return "<style type=\"text/css\">@font-face {font-family: CustomFont;" +
                "src: url(\"file:///android_asset/" +
                LUIUtil.getFontForAll() +
                "\")}" +
                "body {font-family: CustomFont;font-size: medium;text-align: justify;}</style>" +
                (addBodyStart ? "<body>" : "") + html + (addBodyEnd ? "</body>" : "");
    }

    private void updateUIWevViewSize(WebView webView, int size) {
        WebSettings settings = webView.getSettings();
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion <= 14) {
            settings.setTextSize(WebSettings.TextSize.LARGER);
        } else {
            settings.setTextZoom(size);
        }
    }

    public void zoomIn() {
        if (getView() == null) {
            //LLog.d(TAG, "getView null");
            return;
        }
        WebView webView = (WebView) getView().findViewById(R.id.wv);
        if (webView == null) {
            //LLog.d(TAG, "webView null");
            return;
        }
        WebSettings settings = webView.getSettings();
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion <= 14) {
            settings.setTextSize(WebSettings.TextSize.LARGER);
        } else {
            int size = (int) (settings.getTextZoom() * 1.1);
            if (size > 250) {
                size = 250;
            }
            //LLog.d(TAG, "webView size " + size);
            LPref.setTextSizeEpub(getActivity(), size);
            updateUIWevViewSize(webView, size);
        }
    }

    public void zoomOut() {
        if (getView() == null) {
            //LLog.d(TAG, "getView null");
            return;
        }
        WebView webView = (WebView) getView().findViewById(R.id.wv);
        if (webView == null) {
            //LLog.d(TAG, "webView null");
            return;
        }
        WebSettings settings = webView.getSettings();
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion <= 14) {
            settings.setTextSize(WebSettings.TextSize.SMALLEST);
        } else {
            int size = (int) (settings.getTextZoom() / 1.1);
            if (size < 50) {
                size = 50;
            }
            //LLog.d(TAG, "webView size " + size);
            LPref.setTextSizeEpub(getActivity(), size);
            updateUIWevViewSize(webView, size);
        }
    }
}
