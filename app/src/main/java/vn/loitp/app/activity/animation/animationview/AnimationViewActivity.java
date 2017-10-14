package vn.loitp.app.activity.animation.animationview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import vn.loitp.app.base.BaseActivity;
import vn.loitp.app.utilities.LAnimationUtil;
import vn.loitp.app.utilities.LLog;
import vn.loitp.app.utilities.LUIUtil;
import vn.loitp.livestar.R;

public class AnimationViewActivity extends BaseActivity {
    private TextView tvAnim;
    private TextView tvGuide;
    private Button btSelectAnim;

    private List<Techniques> listAnim = new ArrayList<>();
    String[] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvAnim = (TextView) findViewById(R.id.tv_anim);
        tvGuide = (TextView) findViewById(R.id.tv_guide);
        btSelectAnim = (Button) findViewById(R.id.bt_select_anim);

        setupAnimList();

        btSelectAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSelectAnim();
            }
        });
    }

    private void setupAnimList() {
        listAnim = new ArrayList<Techniques>(EnumSet.allOf(Techniques.class));
        arr = new String[listAnim.size()];
        for (int i = 0; i < listAnim.size(); i++) {
            //LLog.d(TAG, result.get(i) + "");
            arr[i] = listAnim.get(i).toString();
        }
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_animation_view;
    }

    private void showDialogSelectAnim() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Choose:");
        builder.setItems(arr, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                LLog.d(TAG, "onClick " + position);
                if (tvGuide.getVisibility() != View.VISIBLE) {
                    tvGuide.setVisibility(View.VISIBLE);
                }
                LUIUtil.setDelay(1000, new LUIUtil.DelayCallback() {
                    @Override
                    public void doAfter(int mls) {
                        LAnimationUtil.play(tvAnim, listAnim.get(position));
                    }
                });
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
