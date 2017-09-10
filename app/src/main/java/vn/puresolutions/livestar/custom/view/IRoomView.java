package vn.puresolutions.livestar.custom.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by Phu Tran on 8/23/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 */
public abstract class IRoomView extends BaseView {

    protected ViewGroup rootView;

    public IRoomView(Context context) {
        super(context);
    }

    public IRoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IRoomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRootView(ViewGroup rootView) {
        this.rootView = rootView;
    }

    public void onResume() { }
    public void onViewVisible() { }

    public abstract void loadData();
}
