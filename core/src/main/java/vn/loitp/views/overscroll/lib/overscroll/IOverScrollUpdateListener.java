package vn.loitp.views.overscroll.lib.overscroll;

/**
 * A callback-listener enabling over-scroll effect clients to subscribe to <b>real-time</b> updates
 * of over-scrolling intensity, provided as the view-translation offset from pre-scroll position.
 *
 * @author amit
 *
 * @see me.everything.android.ui.overscroll.IOverScrollStateListener
 */
public interface IOverScrollUpdateListener {

    /**
     * The invoked callback.
     *
     * @param decor The associated over-scroll 'decorator'.
     * @param state One of: {@link me.everything.android.ui.overscroll.IOverScrollState#STATE_IDLE}, {@link me.everything.android.ui.overscroll.IOverScrollState#STATE_DRAG_START_SIDE},
     *              {@link me.everything.android.ui.overscroll.IOverScrollState#STATE_DRAG_START_SIDE} or {@link IOverScrollState#STATE_BOUNCE_BACK}.
     * @param offset The currently visible offset created due to over-scroll.
     */
    void onOverScrollUpdate(IOverScrollDecor decor, int state, float offset);
}
