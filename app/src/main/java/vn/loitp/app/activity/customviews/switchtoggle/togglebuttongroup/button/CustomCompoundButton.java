package vn.loitp.app.activity.customviews.switchtoggle.togglebuttongroup.button;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;

import vn.loitp.app.R;

public class CustomCompoundButton extends CompoundButton {

    public CustomCompoundButton(Context context) {
        this(context, null);
    }

    public CustomCompoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.customCompoundButtonStyle);
    }

    public CustomCompoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
