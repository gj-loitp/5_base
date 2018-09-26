package vn.loitp.function.epub.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Base64;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import loitp.core.R;
import vn.loitp.core.utilities.LAnimationUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.function.epub.BookSection;
import vn.loitp.utils.util.ConvertUtils;

/**
 * Created by loitp on 08.09.2016.
 */
public class PageFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    public static final String BOOK_SECTION = "BOOK_SECTION";
    private int pxScreenWidth;
    private boolean isPickedWebView;

    public PageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getActivity() == null) {
            return null;
        }
        pxScreenWidth = ((EpubReaderReadActivity) getActivity()).pxScreenWidth;
        isPickedWebView = ((EpubReaderReadActivity) getActivity()).isPickedWebView;
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
        setFragmentView(mainLayout, isPickedWebView, bookSection.getSectionContent(), "text/html", "UTF-8"); // reader.isContentStyled
    }

    public void setFragmentView(RelativeLayout mainLayout, boolean isContentStyled, String data, String mimeType, String encoding) {
        WebView webView = (WebView) mainLayout.findViewById(R.id.wv);
        ScrollView scrollView = (ScrollView) mainLayout.findViewById(R.id.sv);
        TextView textView = (TextView) mainLayout.findViewById(R.id.tv);
        if (isContentStyled) {
            //webView.loadDataWithBaseURL(null, data, mimeType, encoding, null);
            webView.loadDataWithBaseURL("file:///android_asset/",
                    getStyledFont(data),
                    mimeType, encoding, "about:blank");
            textView.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
            textView = null;
            scrollView = null;
            mainLayout.removeView(scrollView);
        } else {
            LUIUtil.setPullLikeIOSVertical(scrollView);
            textView.setText(Html.fromHtml(data, new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {
                    String imageAsStr = source.substring(source.indexOf(";base64,") + 8);
                    byte[] imageAsBytes = Base64.decode(imageAsStr, Base64.DEFAULT);
                    Bitmap imageAsBitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

                    int imageWidthStartPx = (pxScreenWidth - imageAsBitmap.getWidth()) / 2;
                    int imageWidthEndPx = pxScreenWidth - imageWidthStartPx;

                    Drawable imageAsDrawable = new BitmapDrawable(getResources(), imageAsBitmap);
                    imageAsDrawable.setBounds(imageWidthStartPx, 0, imageWidthEndPx, imageAsBitmap.getHeight());
                    return imageAsDrawable;
                }
            }, null));
            webView.setVisibility(View.GONE);
            webView = null;
            mainLayout.removeView(webView);
        }
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

    public void zoomIn() {
        if (getView() == null) {
            //LLog.d(TAG, "getView null");
            return;
        }
        WebView webView = (WebView) getView().findViewById(R.id.wv);
        TextView textView = (TextView) getView().findViewById(R.id.tv);
        if (textView.getVisibility() == View.VISIBLE) {
            //LLog.d(TAG, "textView VISIBLE");
            int sizeDP = ConvertUtils.px2dp(textView.getTextSize()) + 1;
            if (sizeDP >= 45) {
                sizeDP = 45;
            }
            LLog.d(TAG, "textView size " + sizeDP);
            LUIUtil.setTextSize(textView, TypedValue.COMPLEX_UNIT_DIP, sizeDP);
        } else {
            //LLog.d(TAG, "textView !VISIBLE");
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
                LLog.d(TAG, "webView size " + size);
                settings.setTextZoom(size);
            }
        }
    }

}
