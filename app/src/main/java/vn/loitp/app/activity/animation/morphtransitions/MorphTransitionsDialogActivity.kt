package vn.loitp.app.activity.animation.morphtransitions;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.core.app.ActivityCompat;

import com.animation.morphtransitions.FabTransform;
import com.animation.morphtransitions.MorphTransform;
import com.annotation.IsFullScreen;
import com.annotation.IsSwipeActivity;
import com.annotation.LogTag;
import com.core.base.BaseFontActivity;

import vn.loitp.app.R;

@LogTag("DialogActivity")
@IsFullScreen(false)
@IsSwipeActivity(true)
public class MorphTransitionsDialogActivity extends BaseFontActivity {

    private static final String EXTRA_TYPE = "type";

    public static final int TYPE_FAB = 1;
    public static final int TYPE_BUTTON = 2;

    public static Intent newIntent(Context context, int type) {
        Intent intent = new Intent(context, MorphTransitionsDialogActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.root).setOnClickListener(v -> ActivityCompat.finishAfterTransition(MorphTransitionsDialogActivity.this));

        View container = findViewById(R.id.container);

        //This could probably be better. Basically checks to see if it is a FabTransform. If not,
        //tries out being a MorphTransform
        switch (getIntent().getIntExtra(EXTRA_TYPE, -1)) {
            case TYPE_FAB:
                FabTransform.setup(this, container);
                break;
            case TYPE_BUTTON:
                MorphTransform.setup(this, container, Color.WHITE,
                        getResources().getDimensionPixelSize(R.dimen.round_medium));
                break;
        }
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_morph_transtions_dialog;
    }
}
