package com.ogaclejapan.smarttablayout;

/**
 * Created by Phu Tran on 1/2/2016.
 */
public interface TabStateListener {
    void onFirstTabStateChange(boolean isFullyShowing);
    void onLastTabStateChange(boolean isFullyShowing);
}
