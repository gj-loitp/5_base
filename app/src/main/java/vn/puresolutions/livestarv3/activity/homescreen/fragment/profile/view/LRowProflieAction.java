package vn.puresolutions.livestarv3.activity.homescreen.fragment.profile.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import vn.puresolutions.livestar.R;

/**
 * File created on 8/15/2017.
 *
 * @author anhdv
 */

public class LRowProflieAction extends RelativeLayout {
    private Callback callBack;
    private ImageView ivRowProflie_Icon;
    private TextView btnAddCoin;
    private TextView tvMainTitle;
    private TextView tvSubTitle;
    private RelativeLayout rlContainer;

    public void setOnClickItem(Callback callBack) {
        this.callBack = callBack;
    }

    public LRowProflieAction(Context context) {
        super(context);
        init();
    }

    public LRowProflieAction(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LRowProflieAction(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.row_item_manager_profile, this);
        rlContainer = (RelativeLayout) findViewById(R.id.rlRowProfile_Container);
        ivRowProflie_Icon = (ImageView) findViewById(R.id.ivRowProflie_Icon);
        tvMainTitle = (TextView) findViewById(R.id.tvRowProfile_MainTitle);
        tvSubTitle = (TextView) findViewById(R.id.tvRowProfile_SubTitle);
        btnAddCoin = (TextView) findViewById(R.id.btnRowProfile_AddCoin);
        btnAddCoin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.OnClickButton();
            }
        });
        rlContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.OnClickContainer();
            }
        });
        tvMainTitle.setTypeface(null, Typeface.BOLD);
    }

    public interface Callback {
        public void OnClickContainer();

        public void OnClickButton();
    }

    public void setVisibleButton() {
        btnAddCoin.setVisibility(VISIBLE);
    }

    public void setMainTitle(String title) {
        tvMainTitle.setText(title);
    }

    public void setSubTitle(String title) {
        tvSubTitle.setText(title);
    }

    public void setVisibleSubTitle() {
        btnAddCoin.setVisibility(VISIBLE);
    }

    public void setInvisibleButton() {
        btnAddCoin.setVisibility(INVISIBLE);
    }

    public void setIcon(int drawable) {
        ivRowProflie_Icon.setImageResource(drawable);
    }
}
