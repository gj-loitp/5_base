package vn.loitp.app.activity.customviews.edittext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.loitp.app.activity.customviews.edittext.autoresizeedittext.AutoResizeEditTextActivity;
import vn.loitp.app.activity.customviews.edittext.biuedittext.BiuEditTextActivity;
import vn.loitp.app.activity.customviews.edittext.materialtextfield.MaterialTextFieldActivity;
import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LUIUtil;

public class EditTextMenuActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_menu_edittext;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
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
