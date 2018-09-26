package vn.loitp.function.epub.core;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import loitp.core.R;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.function.epub.BookSection;

/**
 * Created by loitp on 08.09.2016.
 */
public class PageFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    public static final String BOOK_SECTION = "BOOK_SECTION";
    private int pxScreenWidth;
    private boolean isUseFont;

    public PageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getActivity() == null) {
            return null;
        }
        pxScreenWidth = ((EpubReaderReadActivity) getActivity()).pxScreenWidth;
        isUseFont = ((EpubReaderReadActivity) getActivity()).isUseFont;
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
        setFragmentView(mainLayout, isUseFont, bookSection.getSectionContent(), "text/html", "UTF-8"); // reader.isContentStyled
    }

    public void setFragmentView(RelativeLayout mainLayout, boolean isContentStyled, String data, String mimeType, String encoding) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (isContentStyled) {
            WebView webView = new WebView(getActivity());
            //webView.loadDataWithBaseURL(null, data, mimeType, encoding, null);
            webView.loadDataWithBaseURL("file:///android_asset/",
                    getStyledFont(data),
                    mimeType, encoding, "about:blank");
            webView.setLayoutParams(layoutParams);
            mainLayout.addView(webView);
        } else {
            ScrollView scrollView = new ScrollView(getActivity());
            LUIUtil.setPullLikeIOSVertical(scrollView);
            scrollView.setLayoutParams(layoutParams);
            TextView textView = new TextView(getActivity());
            textView.setTextColor(Color.BLACK);
            LUIUtil.setTextSize(textView, TypedValue.COMPLEX_UNIT_DIP, 18);
            textView.setLayoutParams(layoutParams);
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
            int pxPadding = dpToPx(10);
            textView.setPadding(pxPadding, pxPadding, pxPadding, pxPadding);
            if (isUseFont) {
                Typeface face = Typeface.createFromAsset(getActivity().getAssets(), LUIUtil.getFontForAll());
                textView.setTypeface(face);
            }
            scrollView.addView(textView);
            mainLayout.addView(scrollView);
        }
    }

    private String getStyledFont(String html) {
        if (!isUseFont) {
            return html;
        }
        boolean addBodyStart = !html.toLowerCase().contains("<body>");
        boolean addBodyEnd = !html.toLowerCase().contains("</body");
        return "<style type=\"text/css\">@font-face {font-family: CustomFont;" +
                "src: url(\"file:///android_asset/" +
                LUIUtil.getFontForAll() +
                "\")}" +
                "body {font-family: CustomFont;font-size: medium;text-align: justify;}</style>" +
                (addBodyStart ? "<body>" : "") + html + (addBodyEnd ? "</body>" : "");
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
