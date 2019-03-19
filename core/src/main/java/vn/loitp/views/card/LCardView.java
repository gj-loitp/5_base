package vn.loitp.views.card;

import android.content.Context;
import androidx.cardview.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import loitp.core.R;
import vn.loitp.core.utilities.LImageUtil;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LCardView extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private CardView cardView;
    private ImageView iv;
    private TextView tv;

    public CardView getCardView() {
        return cardView;
    }

    public ImageView getIv() {
        return iv;
    }

    public TextView getTv() {
        return tv;
    }

    public LCardView(Context context) {
        super(context);
        init();
    }

    public LCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_card_view, this);
        cardView = (CardView) findViewById(R.id.card_view);
        tv = (TextView) findViewById(R.id.tv);
        iv = (ImageView) findViewById(R.id.iv);

        cardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClickRoot(v);
                }
            }
        });

        cardView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (callback != null) {
                    callback.onLongClickRoot(v);
                }
                return true;
            }
        });
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClickText(v);
                }
            }
        });

        tv.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (callback != null) {
                    callback.onLongClickText(v);
                }
                return true;
            }
        });
    }

    public void setHeight(int px) {
        cardView.getLayoutParams().height = px;
        cardView.requestLayout();
    }

    public void setRadius(float px) {
        cardView.setRadius(px);
        //cardView.requestLayout();
    }

    public void setCardBackground(int color) {
        cardView.setCardBackgroundColor(color);
    }

    public void setCardElevation(float elevation) {
        cardView.setCardElevation(elevation);
    }

    public void setImg(String url) {
        LImageUtil.load(getContext(), url, iv);
    }

    public void setText(String s) {
        tv.setText(s);
    }

    public void setText(int res) {
        tv.setText(getContext().getString(res));
    }

    public void setTextColor(int color) {
        tv.setTextColor(color);
    }

    public void setTextShadow(int color) {
        tv.setTextColor(color);
    }

    public interface Callback {
        public void onClickRoot(View v);

        public void onLongClickRoot(View v);

        public void onClickText(View v);

        public void onLongClickText(View v);
    }

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }
}