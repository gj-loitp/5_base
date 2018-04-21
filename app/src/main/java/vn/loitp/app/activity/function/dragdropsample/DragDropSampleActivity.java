package vn.loitp.app.activity.function.dragdropsample;

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;

public class DragDropSampleActivity extends BaseActivity {
    private ImageView paper;
    private ImageView trash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        paper = (ImageView) findViewById(R.id.paper);
        paper.setTag("paper");
        trash = (ImageView) findViewById(R.id.trash);
        trash.setTag("trash");

        trash.setOnDragListener(new TrashDragListener(
                R.mipmap.ic_launcher,
                R.drawable.search_color));

        trash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paper.setVisibility(View.VISIBLE);
            }
        });

        paper.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_drag_drop_sample;
    }

    private static class TrashDragListener implements View.OnDragListener {

        private static final String TAG = "TrashDragListener";

        private int enterShape;
        private int normalShape;
        private boolean hit;

        public TrashDragListener(int enterShape, int normalShape) {
            this.enterShape = enterShape;
            this.normalShape = normalShape;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            final ImageView containerView = (ImageView) v;
            final ImageView draggedView = (ImageView) event.getLocalState();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    LLog.d(TAG, "onDrag: ACTION_DRAG_STARTED");
                    hit = false;
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    LLog.d(TAG, "onDrag: ACTION_DRAG_ENTERED");
                    containerView.setImageResource(enterShape);
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    LLog.d(TAG, "onDrag: ACTION_DRAG_EXITED");
                    containerView.setImageResource(normalShape);
                    return true;
                case DragEvent.ACTION_DROP:
                    LLog.d(TAG, "onDrag: ACTION_DROP");
                    hit = true;
                    draggedView.post(new Runnable() {
                        @Override
                        public void run() {
                            draggedView.setVisibility(View.GONE);
                        }
                    });
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    LLog.d(TAG, "onDrag: ACTION_DRAG_ENDED");
                    containerView.setImageResource(normalShape);
                    v.setVisibility(View.VISIBLE);
                    if (!hit) {
                        draggedView.post(new Runnable() {
                            @Override
                            public void run() {
                                draggedView.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    return true;
                default:
                    return true;
            }
        }
    }

}
