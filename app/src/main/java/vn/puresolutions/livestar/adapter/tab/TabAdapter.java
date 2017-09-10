package vn.puresolutions.livestar.adapter.tab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Phu Tran on 11/9/2015.
 */
public interface TabAdapter {
    Class<? extends Fragment> getFragmentClass(int position);
    String getTitle(Context context, int position);
    int getCount();
    Bundle getArgument(int position);
}
