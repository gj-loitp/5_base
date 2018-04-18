package vn.loitp.views.uizavideo.listerner;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;

import java.util.List;

import vn.loitp.core.common.Constants;
import vn.loitp.core.utilities.LLog;

/**
 * Created by LENOVO on 4/11/2018.
 */

public class TextOutputListener implements TextOutput {
    //private final String TAG = getClass().getSimpleName();
    private final String TAG = Constants.LOITP;

    @Override
    public void onCues(List<Cue> cues) {
        LLog.d(TAG, "onCues " + cues.size());
    }
}
