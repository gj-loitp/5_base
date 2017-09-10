package vn.puresolutions.livestarv3.view;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import vn.puresolutions.livestar.R;

/**
 * File created on 8/15/2017.
 *
 * @author anhdv
 */

public class LEdittextClear extends RelativeLayout {
    private ImageView imvClear;
    private ImageView ivIcon;
    private EditText edtInput;

    public LEdittextClear(Context context) {
        super(context);
        init();
    }

    public LEdittextClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LEdittextClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_input_clear_text, this);
        imvClear = (ImageView) findViewById(R.id.imvClear);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        edtInput = (EditText) findViewById(R.id.edtInput);
        imvClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtInput.getText().toString().length() > 0) {
                    edtInput.setText("");
                    imvClear.setVisibility(INVISIBLE);
                }
            }
        });
        edtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    imvClear.setVisibility(VISIBLE);
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

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setNumType() {
        edtInput.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    public EditText getEditext() {
        return edtInput;
    }

    public String getText() {
        return edtInput.getText().toString().trim();
    }

    public void setHint(int hint) {
        edtInput.setHint(hint);
    }

    public void setImageLeft(int resID) {
        ivIcon.setImageResource(resID);
        ivIcon.setVisibility(VISIBLE);
    }
}
