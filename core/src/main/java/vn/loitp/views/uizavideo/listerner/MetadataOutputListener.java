package vn.loitp.views.uizavideo.listerner;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;

import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LLog;

/**
 * Created by LENOVO on 4/11/2018.
 */

public class MetadataOutputListener implements MetadataOutput {
    //private final String TAG = getClass().getSimpleName();
    private final String TAG = Constants.LOITP;

    @Override
    public void onMetadata(Metadata metadata) {
        LLog.d(TAG, "onMetadata " + metadata.length());
    }
}
