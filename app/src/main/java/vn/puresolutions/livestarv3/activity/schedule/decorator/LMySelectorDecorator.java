package vn.puresolutions.livestarv3.activity.schedule.decorator;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import vn.puresolutions.livestar.R;

/**
 * Use a custom selector
 */
public class LMySelectorDecorator implements DayViewDecorator {

    private final Drawable drawable;

    public LMySelectorDecorator(Activity context) {
        drawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}