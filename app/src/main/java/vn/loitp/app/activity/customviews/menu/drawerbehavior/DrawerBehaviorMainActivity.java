package vn.loitp.app.activity.customviews.menu.drawerbehavior;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import vn.loitp.app.R;
import vn.loitp.app.activity.customviews.menu.drawerbehavior.drawer.Advance3DDrawer1Activity;
import vn.loitp.app.activity.customviews.menu.drawerbehavior.drawer.AdvanceDrawer1Activity;
import vn.loitp.app.activity.customviews.menu.drawerbehavior.drawer.AdvanceDrawer2Activity;
import vn.loitp.app.activity.customviews.menu.drawerbehavior.drawer.AdvanceDrawer3Activity;
import vn.loitp.app.activity.customviews.menu.drawerbehavior.drawer.AdvanceDrawer4Activity;
import vn.loitp.app.activity.customviews.menu.drawerbehavior.drawer.AdvanceDrawer5Activity;
import vn.loitp.app.activity.customviews.menu.drawerbehavior.drawer.AdvanceDrawer6Activity;
import vn.loitp.app.activity.customviews.menu.drawerbehavior.drawer.DefaultDrawerActivity;

public class DrawerBehaviorMainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_behavior_main);

        findViewById(R.id.button_default).setOnClickListener(this);
        findViewById(R.id.button_advance1).setOnClickListener(this);
        findViewById(R.id.button_advance2).setOnClickListener(this);
        findViewById(R.id.button_advance3).setOnClickListener(this);
        findViewById(R.id.button_advance4).setOnClickListener(this);
        findViewById(R.id.button_advance5).setOnClickListener(this);
        findViewById(R.id.button_advance6).setOnClickListener(this);
        findViewById(R.id.button_advance_3d_1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_default:
                startActivity(new Intent(this, DefaultDrawerActivity.class));
                break;
            case R.id.button_advance1:
                startActivity(new Intent(this, AdvanceDrawer1Activity.class));
                break;
            case R.id.button_advance2:
                startActivity(new Intent(this, AdvanceDrawer2Activity.class));
                break;
            case R.id.button_advance3:
                startActivity(new Intent(this, AdvanceDrawer3Activity.class));
                break;
            case R.id.button_advance4:
                startActivity(new Intent(this, AdvanceDrawer4Activity.class));
                break;
            case R.id.button_advance5:
                startActivity(new Intent(this, AdvanceDrawer5Activity.class));
                break;

            case R.id.button_advance6:
                startActivity(new Intent(this, AdvanceDrawer6Activity.class));
                break;
            case R.id.button_advance_3d_1:
                startActivity(new Intent(this, Advance3DDrawer1Activity.class));
                break;
        }
    }
}
