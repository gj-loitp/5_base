package vn.loitp.app.activity.animation.morphtransitions;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.animation.morphtransitions.FabTransform;

import vn.loitp.app.R;

public class FullScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        FabTransform.setup(this, findViewById(R.id.root));
    }
}
