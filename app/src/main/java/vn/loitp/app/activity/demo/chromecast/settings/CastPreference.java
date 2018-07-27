/*
 * Copyright (C) 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vn.loitp.app.activity.demo.chromecast.settings;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;

import loitp.basemaster.R;
import vn.loitp.app.activity.demo.chromecast.utils.ChromeCastUtils;

/**
 * An Preference Activity.
 */
public class CastPreference extends PreferenceActivity
        implements OnSharedPreferenceChangeListener {

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.application_preference);
        getPreferenceScreen().getSharedPreferences().
                registerOnSharedPreferenceChangeListener(this);

        EditTextPreference versionPref = (EditTextPreference) findPreference("app_version");
        versionPref.setTitle(getString(R.string.version, ChromeCastUtils.getAppVersionName(this)));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Handle volume and caption preferences.
    }
}
