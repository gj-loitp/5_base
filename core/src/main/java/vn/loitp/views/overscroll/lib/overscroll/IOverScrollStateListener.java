package vn.loitp.views.overscroll.lib.overscroll;

public interface IOverScrollStateListener {

    /**
     * The invoked callback.
     *
     * @param decor The associated over-scroll 'decorator'.
     * @param oldState The old over-scroll state; ID's specified by {@link me.everything.android.ui.overscroll.IOverScrollState}, e.g.
     *                 {@link me.everything.android.ui.overscroll.IOverScrollState#STATE_IDLE}.
     * @param newState The <b>new</b> over-scroll state; ID's specified by {@link me.everything.android.ui.overscroll.IOverScrollState},
     *                 e.g. {@link me.everything.android.ui.overscroll.IOverScrollState#STATE_IDLE}.
     */
    void onOverScrollStateChange(IOverScrollDecor decor, int oldState, int newState);
}
