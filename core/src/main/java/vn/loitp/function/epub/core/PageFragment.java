package vn.loitp.function.epub.core;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;

import loitp.core.R;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPref;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.function.epub.BookSection;

/**
 * Created by loitp on 08.09.2016.
 */
public class PageFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    public static final String BOOK_SECTION = "BOOK_SECTION";

    public PageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getActivity() == null) {
            return null;
        }
        //LLog.d(TAG, "isPickedWebView " + isPickedWebView);
        return inflater.inflate(R.layout.frm_epub_reader, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RelativeLayout mainLayout = (RelativeLayout) view.findViewById(R.id.fragment_main_layout);
        Bundle bundle = getArguments();
        if (bundle == null) {
            LLog.e(TAG, "bundle == null");
            return;
        }
        BookSection bookSection = (BookSection) bundle.getSerializable(BOOK_SECTION);
        if (bookSection == null) {
            LLog.e(TAG, "bookSection == null");
            return;
        }
        setFragmentView(mainLayout, bookSection.getSectionContent(), "text/html", "UTF-8"); // reader.isContentStyled
    }

    public void setFragmentView(RelativeLayout mainLayout, String data, String mimeType, String encoding) {
        WebView webView = (WebView) mainLayout.findViewById(R.id.wv);
        //webView.loadDataWithBaseURL(null, data, mimeType, encoding, null);
        webView.loadDataWithBaseURL("file:///android_asset/",
                getStyledFont(data),
                mimeType, encoding, "about:blank");
        int size = LPref.getTextSizeEpub(getActivity());
        updateUIWevViewSize(webView, size);
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
                LAnimationUtil.play(webView, Techniques.Pulse);
                size = 250;
            }
            //LLog.d(TAG, "webView size " + size);
            LPref.setTextSizeEpub(getActivity(), size);
            updateUIWevViewSize(webView, size);
        }
    }
}
