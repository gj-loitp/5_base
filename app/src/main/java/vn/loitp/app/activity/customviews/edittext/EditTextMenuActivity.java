package vn.loitp.app.activity.customviews.edittext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.edittext.animatedexpandableedittext.AnimatedExpandableEditTextActivity;
import vn.loitp.app.activity.customviews.edittext.autoresizeedittext.AutoResizeEditTextActivity;
import vn.loitp.app.activity.customviews.edittext.biuedittext.BiuEditTextActivity;
import vn.loitp.app.activity.customviews.edittext.materialtextfield.MaterialTextFieldActivity;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;

public class EditTextMenuActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_animated_expandable_edit_text).setOnClickListener(this);
        findViewById(R.id.bt_auto_resize_edit_text).setOnClickListener(this);
        findViewById(R.id.bt_material_text_field).setOnClickListener(this);
        findViewById(R.id.bt_biu_edit_text).setOnClickListener(this);
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
        return R.layout.activity_menu_edittext;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_animated_expandable_edit_text:
                intent = new Intent(activity, AnimatedExpandableEditTextActivity.class);
                break;
            case R.id.bt_auto_resize_edit_text:
                intent = new Intent(activity, AutoResizeEditTextActivity.class);
                break;
            case R.id.bt_material_text_field:
                intent = new Intent(activity, MaterialTextFieldActivity.class);
                break;
            case R.id.bt_biu_edit_text:
                intent = new Intent(activity, BiuEditTextActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}
