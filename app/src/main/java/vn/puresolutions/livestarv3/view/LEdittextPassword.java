package vn.puresolutions.livestarv3.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.utilities.v3.LAnimationUtil;

/**
 * File created on 8/14/2017.
 *
 * @author anhdv
 */

public class LEdittextPassword extends RelativeLayout {
    private ImageView ivShowPass;
    private ImageView ivIcon;
    private EditText etInputPass;
    private boolean isClicked;

    public LEdittextPassword(Context context) {
        super(context);
        init();
    }

    public LEdittextPassword(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LEdittextPassword(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_input_password, this);
        ivShowPass = (ImageView) findViewById(R.id.iv_show_pass);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        etInputPass = (EditText) findViewById(R.id.lepInputPass);
        ivShowPass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClicked) {
                    etInputPass.setTransformationMethod(new PasswordTransformationMethod());
                    isClicked = false;
                } else {
                    isClicked = true;
                    etInputPass.setTransformationMethod(null);
                }
                etInputPass.setSelection(etInputPass.getText().toString().trim().length());
                LAnimationUtil.play(v, Techniques.FlipInX);
            }
        });
        etInputPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    ivShowPass.setVisibility(GONE);
                } else {
                    if (ivShowPass.getVisibility() != VISIBLE) {
                        ivShowPass.setVisibility(VISIBLE);
                    }
                }
                if (callback != null) {
                    callback.onTextChange(s, start, before, count);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
            }
        });
    }

    public interface Callback {
        public void onTextChange(CharSequence s, int start, int before, int count);
    }

    private LEdittextClear.Callback callback;

    public void setCallback(LEdittextClear.Callback callback) {
        this.callback = callback;
    }

    public EditText getEditext() {
        return etInputPass;
    }

    public String getPassword() {
        return etInputPass.getText().toString().trim();
    }

    public void setHint(int hint) {
        etInputPass.setHint(hint);
    }

    public void clearText() {
        etInputPass.setText("");
    }

    public void setImageLeft(int resID) {
        ivIcon.setImageResource(resID);
        ivIcon.setVisibility(VISIBLE);
    }

    public String getText() {
        return etInputPass.getText().toString().trim();
    }
}
