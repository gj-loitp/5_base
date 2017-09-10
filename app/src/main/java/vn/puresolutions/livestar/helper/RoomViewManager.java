package vn.puresolutions.livestar.helper;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import vn.puresolutions.livestar.custom.view.IRoomView;
import vn.puresolutions.livestarv3.utilities.old.TextUtils;

/**
 * Created by Phu Tran on 8/23/2016.
 * Email: Phu.TranHoang@harveynash.vn
 * Harvey Nash Vietnam
 */
public class RoomViewManager {

    public static int FORWARD = 0;
    public static int REVERSE = 1;

    private final ViewGroup root;
    private List<Builder> views = new ArrayList<>();

    public RoomViewManager(ViewGroup root, View dismissView) {
        this.root = root;
        dismissView.setOnTouchListener((view, event) -> {
            boolean captureOnTouchEvent = false;
            for (Builder item : views) {
                if (item.isShow) {
                    captureOnTouchEvent = true;
                    break;
                }
            }

            View inputView = ((Activity) view.getContext()).getCurrentFocus();
            if (inputView != null && inputView instanceof EditText) {
                captureOnTouchEvent = true;
                TextUtils.hideKeyboardWhenNotFocus((Activity) view.getContext());

                inputView.clearFocus();
            }
            dismissViews();

            return captureOnTouchEvent;
        });
    }

    public Builder addView(IRoomView roomView, ImageView actionView) {
        Builder holder = new Builder(roomView, actionView);
        views.add(holder);
        return holder;
    }

    public void loadData() {
        if (views.size() > 0) {
            for (Builder view : views) {
                view.roomView.loadData();
            }
        }
    }

    public void onDestroy() {
        if (views.size() > 0) {
            for (Builder view : views) {
                view.roomView.onDestroy();
            }
        }
    }

    public void onResume() {
        if (views.size() > 0) {
            for (Builder view : views) {
                view.roomView.onResume();
            }
        }
    }

    public void dismissViews() {
        if (views.size() > 0) {
            for (Builder holder : views) {
                holder.toggedRoomView(false);
            }
        }
    }

    public class Builder {

        static final long DEFAULT_DURATION = 300;

        IRoomView roomView;
        ImageView actionView;
        boolean translateX;
        long duration = DEFAULT_DURATION;
        int deActiveRes;
        int activeRes;
        int translateDirection;
        boolean isShow;

        Builder(IRoomView roomView, ImageView actionView) {
            this.roomView = roomView;
            this.actionView = actionView;
        }

        public Builder translateX(int direction) {
            translateX = true;
            translateDirection = direction;
            return this;
        }

        public Builder activeImageResource(@DrawableRes int resourceId) {
            activeRes = resourceId;
            return this;
        }

        public Builder deActiveImageResource(@DrawableRes int resourceId) {
            deActiveRes = resourceId;
            return this;
        }

        public Builder translateY() {
            translateX = false;
            return this;
        }

        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public void build() {
            roomView.setRootView(root);
            roomView.post(() -> toggedRoomView(false, 0));
            if (actionView != null) {
                actionView.setOnClickListener(v -> toggedRoomView(true));
            }
        }

        void toggedRoomView(boolean isShow, long duration) {
            this.isShow = isShow;

            if (actionView != null) {
                if (isShow && activeRes > 0) {
                    actionView.setImageResource(activeRes);
                } else if (!isShow && deActiveRes > 0) {
                    actionView.setImageResource(deActiveRes);
                }
            }
            ViewPropertyAnimator animate = roomView.animate();
            if (translateX) {
                if (translateDirection == FORWARD) {
                    animate.translationX(isShow ? 0 : -roomView.getWidth());
                } else {
                    animate.translationX(isShow ? 0 : roomView.getWidth());
                }
            } else {
                animate.translationY(isShow ? 0 : roomView.getHeight());
            }
            animate.setDuration(duration).start();

            if (isShow) {
                roomView.onViewVisible();
            }
        }

        public void toggedRoomView(boolean isShow) {
            toggedRoomView(isShow, duration);
        }
    }
}
