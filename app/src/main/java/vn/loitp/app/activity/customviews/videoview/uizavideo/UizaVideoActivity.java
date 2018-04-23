/*
 * Copyright 2016 The Android Open Source Project
 * Copyright 2017 Rúben Sousa
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vn.loitp.app.activity.customviews.videoview.uizavideo;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LScreenUtil;
import vn.loitp.views.uizavideo.view.util.UizaUtil;

public class UizaVideoActivity extends BaseActivity {
    private FrameLayout frmUizaVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frmUizaVideo = (FrameLayout) findViewById(R.id.frm_uiza_video);
        FrmUizaVideo frm = new FrmUizaVideo();
        LScreenUtil.replaceFragment(activity, frmUizaVideo.getId(), frm, false);
        frm.setFragmentCallback(new BaseFragment.FragmentCallback() {
            @Override
            public void onViewCreated() {
                LLog.d(TAG, "onViewCreated");
                frm.play(getString(R.string.url_dash), getString(R.string.url_thumbnails));
            }
        });
        UizaUtil.resizeLayout(frmUizaVideo, null);
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return TAG;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.uiza_video_activity;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        UizaUtil.resizeLayout(frmUizaVideo, null);
    }
}
