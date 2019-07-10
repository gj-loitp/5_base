package com.views.layout.dragueur;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;

public abstract class ReturnOriginViewAnimator<D extends DraggableView> implements ViewAnimator<D> {

    public static final int ANIMATION_RETURN_TO_ORIGIN_DURATION = 500;

    public void notifyDraggableViewUpdated(@NonNull final D draggableView) {
        DraggableView.DraggableViewListener dragListener = draggableView.getDragListener();
        if (dragListener != null) {
            draggableView.update();
            dragListener.onDrag(draggableView, draggableView.getPercentX(), draggableView.getPercentY());
        }
    }

    @Override
    public boolean animateToOrigin(@NonNull final D draggableView, final int duration) {
        draggableView.setAnimating(true);

        ViewCompat.animate(draggableView)
                .withLayer()
                .translationX(draggableView.getOriginalViewX())
                .translationY(draggableView.getOriginalViewY())
                .rotation(0f)

                .setUpdateListener(new ViewPropertyAnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(View view) {
                        notifyDraggableViewUpdated(draggableView);
                    }
                })

                .setDuration(ANIMATION_RETURN_TO_ORIGIN_DURATION)

                .setListener(new ViewPropertyAnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(View view) {
                        DraggableView.DraggableViewListener dragListener = draggableView.getDragListener();
                        if (dragListener != null) {
                            dragListener.onDragCancelled(draggableView);
                            dragListener.onDrag(draggableView, 0, 0);
                        }
                        draggableView.setAnimating(false);
                    }
                });

        return true;
    }

    @Override
    public boolean animateExit(@NonNull final D draggableView, final Direction direction, int duration) {
        return false;
    }

    @Override
    public void update(D draggableView, float percentX, float percentY) {
    }
}
