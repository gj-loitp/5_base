package com.views.calendar.cosmocalendar.view.customviews;

import android.content.Context;

import androidx.appcompat.widget.AppCompatTextView;

//TODO convert kotlin
public class SquareTextView extends AppCompatTextView {

    public SquareTextView(Context context) {
        super(context);
    }

    //Square view
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
