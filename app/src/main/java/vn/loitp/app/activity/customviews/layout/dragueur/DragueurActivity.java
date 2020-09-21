package vn.loitp.app.activity.customviews.layout.dragueur;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.annotation.IsFullScreen;
import com.annotation.LayoutId;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.views.layout.dragueur.Direction;
import com.views.layout.dragueur.DraggableView;

import vn.loitp.app.R;

//https://github.com/Meetic/Dragueur?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=3534

@LayoutId((R.layout.activity_dragueur))
@LogTag("DragueurActivity")
@IsFullScreen(false)
public class DragueurActivity extends BaseFontActivity {
    private DraggableView draggableView;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        draggableView = findViewById(R.id.dragueur);
        tv = findViewById(R.id.textView);
        draggableView.setRotationEnabled(true);
        //draggableView.setAnimating(true);
        draggableView.setRotationValue(10f);
        //draggableView.setDraggable(true);
        draggableView.setDragListener(new DraggableView.DraggableViewListener() {
            @Override
            public void onDrag(DraggableView draggableView, float percentX, float percentY) {
                setText("draggableView: " + percentX + " - " + percentY);
                LLog.d(getLogTag(), "onDrag " + percentX + " x " + percentY);
            }

            @Override
            public void onDraggedStarted(DraggableView draggableView, Direction direction) {
                setText("onDraggedStarted");
                LLog.d(getLogTag(), "onDraggedStarted " + direction.name());
            }

            @Override
            public void onDraggedEnded(DraggableView draggableView, Direction direction) {
                setText("onDraggedEnded");
                LLog.d(getLogTag(), "onDraggedEnded " + direction.name());
            }

            @Override
            public void onDragCancelled(DraggableView draggableView) {
                setText("onDragCancelled");
            }
        });
        /*draggableView.setViewAnimator(new ViewAnimator() {
            @Override
            public boolean animateExit(@NonNull DraggableView draggableView, Direction direction, int duration) {
                return false;
            }

            @Override
            public boolean animateToOrigin(@NonNull DraggableView draggableView, int duration) {
                return false;
            }

            @Override
            public void update(@NonNull DraggableView draggableView, float percentX, float percentY) {
                //do nothing
            }
        });*/
    }

    @SuppressLint("SetTextI18n")
    private void setText(String s) {
        tv.setText("Dragueur can move any view with one finger ;)\n" + s);
    }
}
