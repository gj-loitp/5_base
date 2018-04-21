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

package vn.loitp.app.activity.customviews.videoview.exoplayer2withpreviewseekbar.videowithpreviewseekbar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.github.rubensousa.previewseekbar.base.PreviewView;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBar;
import com.github.rubensousa.previewseekbar.exoplayer.PreviewTimeBarLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import loitp.basemaster.R;
import vn.loitp.app.activity.customviews.videoview.exoplayer2withpreviewseekbar.videowithpreviewseekbar.exoplayer.ExoPlayerManagerPB;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LActivityUtil;
import vn.loitp.core.utilities.LLog;

public class PBMainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, PreviewView.OnPreviewChangeListener {
    private static final int PICK_FILE_REQUEST_CODE = 2;
    private ExoPlayerManagerPB exoPlayerManagerPB;
    private PreviewTimeBarLayout previewTimeBarLayout;
    private PreviewTimeBar previewTimeBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleExoPlayerView simpleExoPlayerView = findViewById(R.id.player_view);

        previewTimeBar = simpleExoPlayerView.findViewById(R.id.exo_progress);
        previewTimeBarLayout = findViewById(R.id.previewSeekBarLayout);
        previewTimeBarLayout.setTintColorResource(R.color.colorPrimary);
        previewTimeBar.addOnPreviewChangeListener(this);

        exoPlayerManagerPB = new ExoPlayerManagerPB(simpleExoPlayerView, previewTimeBarLayout, (ImageView) findViewById(R.id.imageView), getString(R.string.url_thumbnails));
        exoPlayerManagerPB.play(Uri.parse(getString(R.string.url_dash)));
        //exoPlayerManagerPB.play(Uri.parse(getString(R.string.url_smooth)));
        //exoPlayerManagerPB.play(Uri.parse(getString(R.string.url_hls)));
        //exoPlayerManagerPB.play(Uri.parse(getString(R.string.url_mp3)));
        previewTimeBarLayout.setPreviewLoader(exoPlayerManagerPB);
        requestFullScreenIfLandscape();
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
        return R.layout.activity_main_pb;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            exoPlayerManagerPB.play(data.getData());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (exoPlayerManagerPB != null) {
            exoPlayerManagerPB.onStart();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (exoPlayerManagerPB != null) {
            exoPlayerManagerPB.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayerManagerPB != null) {
            exoPlayerManagerPB.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayerManagerPB != null) {
            exoPlayerManagerPB.onStop();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_local) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("video/*.mp4");
            startActivityForResult(intent, PICK_FILE_REQUEST_CODE);
        } else if (item.getItemId() == R.id.action_toggle) {
            if (previewTimeBarLayout.isShowingPreview()) {
                previewTimeBarLayout.hidePreview();
                exoPlayerManagerPB.stopPreview();
            } else {
                previewTimeBarLayout.showPreview();
                exoPlayerManagerPB.loadPreview(previewTimeBar.getProgress(),
                        previewTimeBar.getDuration());
            }
        } else {
            startActivity(new Intent(this, PBLocalActivity.class));
            LActivityUtil.tranIn(activity);
        }
        return true;
    }

    private void requestFullScreenIfLandscape() {
        if (getResources().getBoolean(R.bool.landscape)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        } else {
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.inflateMenu(R.menu.main_pb);
            toolbar.setOnMenuItemClickListener(this);
        }
    }

    @Override
    public void onStartPreview(PreviewView previewView) {
        if (getResources().getBoolean(R.bool.landscape)) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN);
        }
    }

    @Override
    public void onStopPreview(PreviewView previewView) {
        if (exoPlayerManagerPB != null) {
            exoPlayerManagerPB.stopPreview();
        }
    }

    @Override
    public void onPreview(PreviewView previewView, int progress, boolean fromUser) {

    }
}
