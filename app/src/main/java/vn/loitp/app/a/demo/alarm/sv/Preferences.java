package vn.loitp.app.a.demo.alarm.sv;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import vn.loitp.R;

public class Preferences extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.preferences);
    }
}
