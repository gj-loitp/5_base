package vn.loitp.app.activity.customviews.bottomnavigationbar.bottombar.lib;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.daimajia.androidanimations.library.Techniques;

import vn.loitp.app.utilities.LAnimationUtil;
import vn.loitp.livestar.R;

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

public class LBottomBar extends RelativeLayout implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    //private LinearLayout ll0;
    //private LinearLayout ll1;
    //private FrameLayout ll2;
    //private LinearLayout ll3;
    //private LinearLayout ll4;

    private ImageView btIcon0;
    private ImageView btIcon1;
    private ImageView btIcon2;
    private ImageView btIcon3;
    private ImageView btIcon4;

    private int previousPos;
    private int currentPos;
    public final static int PAGE_NONE = -1;
    public final static int PAGE_0 = 0;
    public final static int PAGE_1 = 1;
    public final static int PAGE_2 = 2;
    public final static int PAGE_3 = 3;
    public final static int PAGE_4 = 4;

    public LBottomBar(Context context) {
        super(context);
        init();
    }

    public LBottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LBottomBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_l_bottom_bar, this);

        //this.ll0 = (LinearLayout) findViewById(R.id.ll_0);
        //this.ll1 = (LinearLayout) findViewById(R.id.ll_1);
        //this.ll2 = (FrameLayout) findViewById(R.id.ll_2);
        //this.ll3 = (LinearLayout) findViewById(R.id.ll_3);
        //this.ll4 = (LinearLayout) findViewById(R.id.ll_4);

        this.btIcon0 = (ImageView) findViewById(R.id.bt_icon_0);
        this.btIcon1 = (ImageView) findViewById(R.id.bt_icon_1);
        this.btIcon2 = (ImageView) findViewById(R.id.bt_icon_2);
        this.btIcon3 = (ImageView) findViewById(R.id.bt_icon_3);
        this.btIcon4 = (ImageView) findViewById(R.id.bt_icon_4);

        btIcon0.setOnClickListener(this);
        btIcon1.setOnClickListener(this);
        btIcon2.setOnClickListener(this);
        btIcon3.setOnClickListener(this);
        btIcon4.setOnClickListener(this);

        updateView(btIcon0);
    }

    @Override
    public void onClick(View v) {
        //LLog.d(TAG, "onClickRootView");
        switch (v.getId()) {
            case R.id.bt_icon_0:
                if (currentPos != PAGE_0) {
                    previousPos = currentPos;
                    currentPos = PAGE_0;
                    onClickItem(currentPos);
                    updateView(btIcon0);
                }
                break;
            case R.id.bt_icon_1:
                if (currentPos != PAGE_1) {
                    previousPos = currentPos;
                    currentPos = PAGE_1;
                    onClickItem(currentPos);
                    updateView(btIcon1);
                }
                break;
            case R.id.bt_icon_2:
                if (currentPos != PAGE_2) {
                    previousPos = currentPos;
                    currentPos = PAGE_2;
                    onClickItem(currentPos);
                    updateView(btIcon2);
                }
                break;
            case R.id.bt_icon_3:
                if (currentPos != PAGE_3) {
                    previousPos = currentPos;
                    currentPos = PAGE_3;
                    onClickItem(currentPos);
                    updateView(btIcon3);
                }
                break;
            case R.id.bt_icon_4:
                if (currentPos != PAGE_4) {
                    previousPos = currentPos;
                    currentPos = PAGE_4;
                    onClickItem(currentPos);
                    updateView(btIcon4);
                }
                break;
        }
    }

    private void updateView(ImageView imageView) {
        LAnimationUtil.play(imageView, Techniques.Pulse);
        btIcon0.setColorFilter(ContextCompat.getColor(getContext(), R.color.Black));
        btIcon1.setColorFilter(ContextCompat.getColor(getContext(), R.color.Black));
        btIcon2.setColorFilter(ContextCompat.getColor(getContext(), R.color.Black));
        btIcon3.setColorFilter(ContextCompat.getColor(getContext(), R.color.Black));
        btIcon4.setColorFilter(ContextCompat.getColor(getContext(), R.color.Black));
        imageView.setColorFilter(ContextCompat.getColor(getContext(), R.color.bottom_press_icon));
    }

    private void onClickItem(int pos) {
        if (callback != null) {
            callback.OnClickItem(pos);
        }
    }

    private Callback callback;

    public interface Callback {
        public void OnClickItem(int position);
    }

    public void setOnItemClick(Callback callback) {
        this.callback = callback;
    }

    public void setPerformItemClick(int position) {
        //LLog.d(TAG, "setPerformItemClick " + position);
        previousPos = currentPos;
        currentPos = position;
        switch (position) {
            case PAGE_0:
                onClickItem(PAGE_0);
                updateView(btIcon0);
                break;
            case PAGE_1:
                onClickItem(PAGE_1);
                updateView(btIcon1);
                break;
            case PAGE_2:
                onClickItem(PAGE_2);
                updateView(btIcon2);
                break;
            case PAGE_3:
                onClickItem(PAGE_3);
                updateView(btIcon3);
                break;
            case PAGE_4:
                onClickItem(PAGE_4);
                updateView(btIcon4);
                break;
        }
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public int getPreviousPos() {
        return previousPos;
    }
}