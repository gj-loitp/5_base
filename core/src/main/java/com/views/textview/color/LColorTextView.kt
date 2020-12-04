package com.views.textview.color;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.R;

import java.util.ArrayList;
import java.util.List;
//TODO convert kotlin
public class LColorTextView extends AppCompatTextView {
    private List<String> mColorTexts = new ArrayList<>();
    private List<String> mColors = new ArrayList<>();
    private int size;
    private String mCurrentText;

    public LColorTextView(Context context) {
        super(context);
    }

    public LColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LColorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        final TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.LColorTextView, 0, 0);
        final String colorTexts = attrsArray.getString(R.styleable.LColorTextView_color_texts);
        final String colors = attrsArray.getString(R.styleable.LColorTextView_color_arrays);
        attrsArray.recycle();

        initData(colorTexts, colors);

        setHtmlText();
    }

    private void initData(final String colorTexts, final String colors) {
        try {
            if (!TextUtils.isEmpty(colorTexts)) {
                String[] texts = colorTexts.split("\\|");
                for (int i = 0; i < texts.length; i++) {
                    mColorTexts.add(texts[i]);
                }
            }

            if (!TextUtils.isEmpty(colors)) {
                String[] texts = colors.split("\\|");
                for (int i = 0; i < texts.length; i++) {
                    mColors.add(texts[i]);
                }
            }
            size = Math.min(mColors.size(), mColorTexts.size());
            mCurrentText = getText().toString();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void setHtmlText() {
        try {
            if (!TextUtils.isEmpty(mCurrentText)) {
                for (int i = 0; i < size; i++) {
                    mCurrentText = mCurrentText.replace(mColorTexts.get(i), color(mColors.get(i), mColorTexts.get(i)));
                }
            }
            if (!TextUtils.isEmpty(mCurrentText)) {
                setText(Html.fromHtml(mCurrentText));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public LColorTextView findAndSetStrColor(String str, String color) {
        try {
            if (!TextUtils.isEmpty(mCurrentText)) {
                mCurrentText = mCurrentText.replaceAll(str, color(color, str));
            }
            if (!TextUtils.isEmpty(mCurrentText)) {
                setText(Html.fromHtml(mCurrentText));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    public String color(String colorCode, String str) {
        return "<font color=\"" + colorCode + "\">" + str + "</font>";
    }
}
