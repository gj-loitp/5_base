package vn.puresolutions.livestarv3.view;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.utilities.v3.LUIUtil;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LChooseView extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();
    private ImageView ivCheck;
    private TextView tvContent;
    private TextView tvInfo;
    private CardView llMain;

    public ImageView getIvCheck() {
        return ivCheck;
    }

    public TextView getTvContent() {
        return tvContent;
    }

    public LChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LChooseView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_choose_view, this);

        this.llMain = (CardView) findViewById(R.id.ll_main);
        this.ivCheck = (ImageView) findViewById(R.id.iv_check);
        this.tvContent = (TextView) findViewById(R.id.tv_content);
        this.tvInfo = (TextView) findViewById(R.id.tv_info);

        LUIUtil.setMarquee(tvContent);
        LUIUtil.setMarquee(tvInfo);

        llMain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onClick();
                }
            }
        });

        setChecked(false);
    }

    public interface Callback {
        public void onClick();
    }

    private Callback callback;

    public void setOnItemClick(Callback callback) {
        this.callback = callback;
    }

    public void setChecked(boolean isChecked) {
        if (isChecked) {
            ivCheck.setVisibility(VISIBLE);
            tvContent.setTextColor(ContextCompat.getColor(getContext(), R.color.White));
            llMain.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.DarkCyan));
            if (tvInfo.getVisibility() == VISIBLE) {
                tvInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.White));
            }
        } else {
            ivCheck.setVisibility(INVISIBLE);
            tvContent.setTextColor(ContextCompat.getColor(getContext(), R.color.Black));
            llMain.setCardBackgroundColor(ContextCompat.getColor(getContext(), R.color.White));
            if (tvInfo.getVisibility() == VISIBLE) {
                tvInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.Gray));
            }
        }
    }

    public void setTextTvContent(String content) {
        tvContent.setText(content);
    }

    public String getTextTvContent() {
        return tvContent.getText().toString();
    }

    public void setTextTvInfo(String info) {
        if (tvInfo.getVisibility() == GONE) {
            tvInfo.setVisibility(VISIBLE);
        }
        tvInfo.setText(info);
    }

    public String getTextTvInfo() {
        return tvInfo.getText().toString();
    }
}