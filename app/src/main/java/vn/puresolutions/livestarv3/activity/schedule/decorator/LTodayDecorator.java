package vn.puresolutions.livestarv3.activity.schedule.decorator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import vn.puresolutions.livestar.R;

/**
 * Created by www.muathu@gmail.com on 8/4/2017.
 */

public class LTodayDecorator implements DayViewDecorator {
    private Drawable highlightDrawable;
    private Context context;

    public LTodayDecorator(Context context) {
        this.context = context;
        highlightDrawable = this.context.getResources().getDrawable(R.mipmap.ic_launcher);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day.equals(CalendarDay.today());
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(highlightDrawable);
        view.addSpan(new ForegroundColorSpan(Color.GREEN));
        view.addSpan(new StyleSpan(Typeface.BOLD));
        view.addSpan(new RelativeSizeSpan(1.5f));
    }
}
