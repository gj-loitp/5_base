package vn.loitp.views.hottagkeywords.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import loitp.core.R;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class Label extends LinearLayout {

    private TextView mTextView;
    private OnClickCrossListener listenerOnCrossClick;
    private OnLabelClickListener listenerOnLabelClick;

    public Label(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Label(Context context, int textSize, int iconCross,
                 boolean showCross, int textColor, int backgroundResource, boolean labelsClickables, int padding) {
        super(context);
        init(context, textSize, iconCross, showCross, textColor,
                backgroundResource, labelsClickables, padding);
    }

    private void init(final Context context, int textSize, int iconCross,
                      boolean showCross, int textColor, int backgroundResource, boolean labelsClickables, int padding) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View labelView = inflater.inflate(R.layout.label_view, this, true);

        LinearLayout linearLayout = (LinearLayout) labelView.findViewById(R.id.llLabel);
        linearLayout.setBackgroundResource(backgroundResource);
        linearLayout.setPadding(padding, padding, padding, padding);

        if (labelsClickables) {
            linearLayout.setClickable(true);
            linearLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listenerOnLabelClick != null) {
                        listenerOnLabelClick.onClickLabel((Label) labelView);
                    }
                }
            });
        }

        mTextView = (TextView) labelView.findViewById(R.id.tvLabel);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mTextView.setTextColor(textColor);

        ImageView imageView = (ImageView) labelView.findViewById(R.id.ivCross);

        if (showCross) {
            imageView.setImageResource(iconCross);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listenerOnCrossClick != null) {
                        listenerOnCrossClick.onClickCross((Label) labelView);
                    }
                }
            });
        } else {
            imageView.setVisibility(GONE);
        }

    }

    public String getText() {
        return mTextView.getText().toString();
    }

    public void setText(String text) {
        mTextView.setText(text);
    }

    /**
     * Set a callback listener when the cross icon is clicked.
     *
     * @param listener Callback instance.
     */
    public void setOnClickCrossListener(OnClickCrossListener listener) {
        this.listenerOnCrossClick = listener;
    }

    /**
     * Interface for a callback listener when the cross icon is clicked.
     */
    public interface OnClickCrossListener {

        /**
         * Call when the cross icon is clicked.
         */
        void onClickCross(Label label);
    }

    /**
     * Set a callback listener when the {@link Label} is clicked.
     *
     * @param listener Callback instance.
     */
    public void setOnLabelClickListener(OnLabelClickListener listener) {
        this.listenerOnLabelClick = listener;
    }

    /**
     * Interface for a callback listener when the {@link Label} is clicked.
     * Container Activity/Fragment must implement this interface.
     */
    public interface OnLabelClickListener {

        /**
         * Call when the {@link Label} is clicked.
         */
        void onClickLabel(Label label);
    }
}
