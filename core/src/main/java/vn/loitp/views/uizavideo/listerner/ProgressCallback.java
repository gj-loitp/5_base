package vn.loitp.views.uizavideo.listerner;

/**
 * Created by LENOVO on 4/12/2018.
 */

public interface ProgressCallback {
    public void onAdProgress(float currentMls, float duration, int percent);

    public void onVideoProgress(float currentMls, float duration, int percent);
}
