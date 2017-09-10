package vn.puresolutions.livestarv3.view;

/**
 * Created by www.muathu@gmail.com on 9/1/2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import vn.puresolutions.livestar.R;

/**
 * @author loitp
 */

public class LSpinner extends Spinner {
    private static final String TAG = LSpinner.class.getSimpleName();
    private OnSpinnerEventsListener mListener;
    private boolean mOpenInitiated = false;
    private ArrayAdapter spinnerArrayAdapter;

    public LSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public LSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LSpinner(Context context, int mode) {
        super(context, mode);
    }

    public LSpinner(Context context) {
        super(context);
    }

    public interface OnSpinnerEventsListener {

        void onSpinnerOpened();

        void onSpinnerClosed();

    }

    @Override
    public boolean performClick() {
        // register that the Spinner was opened so we have a status
        // indicator for the activity(which may lose focus for some other
        // reasons)
        mOpenInitiated = true;
        if (mListener != null) {
            mListener.onSpinnerOpened();
        }
        return super.performClick();
    }

    public void setSpinnerEventsListener(OnSpinnerEventsListener onSpinnerEventsListener) {
        mListener = onSpinnerEventsListener;
    }

    /**
     * Propagate the closed Spinner event to the listener from outside.
     */
    public void performClosedEvent() {
        mOpenInitiated = false;
        if (mListener != null) {
            mListener.onSpinnerClosed();
        }
    }

    /**
     * A boolean flag indicating that the Spinner triggered an open event.
     *
     * @return true for opened Spinner
     */
    public boolean hasBeenOpened() {
        return mOpenInitiated;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        //android.util.Log.d(TAG, "onWindowFocusChanged");
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasBeenOpened() && hasWindowFocus) {
            //android.util.Log.i(TAG, "closing popup");
            performClosedEvent();
        }
    }

    public void initializeStringValues(String[] values) {
        //spinnerArrayAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item);
        spinnerArrayAdapter = new ArrayAdapter<>(this.getContext(), R.layout.row_spinner_item);
        spinnerArrayAdapter.addAll(values);
        setAdapter(spinnerArrayAdapter);
    }

    public void initializeStringValues(String[] values, String promptText) {
        //spinnerArrayAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item) {
        spinnerArrayAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.row_spinner_item) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                int current = getSelectedItemPosition();
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                //LUIUtil.setMarquee(tv);
                //tv.setGravity(Gravity.CENTER);
                tv.setAllCaps(true);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                    tv.setBackgroundColor(Color.TRANSPARENT);
                } else if (position == current) {
                    tv.setTextColor(Color.WHITE);
                    tv.setBackgroundResource(R.drawable.bt_violet_spinner);
                } else {
                    tv.setTextColor(Color.BLACK);
                    tv.setBackgroundColor(Color.TRANSPARENT);
                }
                return view;
            }
        };
        spinnerArrayAdapter.add(promptText);
        spinnerArrayAdapter.addAll(values);
        setAdapter(spinnerArrayAdapter);
    }

    public
    @Nullable
    Object getItem(int position) {
        if (spinnerArrayAdapter != null && position < spinnerArrayAdapter.getCount()) {
            return spinnerArrayAdapter.getItem(position);
        } else {
            return null;
        }
    }
}