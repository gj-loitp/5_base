package vn.puresolutions.livestarv3.activity.userprofile;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vn.puresolutions.livestar.R;
import vn.puresolutions.livestarv3.activity.userprofile.adapter.VideoManagerAdapter;
import vn.puresolutions.livestarv3.activity.userprofile.model.SectionVideo;
import vn.puresolutions.livestarv3.activity.userprofile.model.SingleVideo;
import vn.puresolutions.livestarv3.base.BaseActivity;

public class VideoManagerActivity extends BaseActivity implements VideoManagerAdapter.Callback {
    private ArrayList<SectionVideo> lstSectionVideo;
    @BindView(R.id.rvVideoManager)
    RecyclerView rvVideoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        createDummyData();
        rvVideoManager.setHasFixedSize(true);
        rvVideoManager.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        VideoManagerAdapter sectionVideoAdapter = new VideoManagerAdapter(this, lstSectionVideo, new VideoManagerAdapter.Callback() {
            @Override
            public void onClick() {
                //Toast.makeText(VideoManagerActivity.this, "XXX", Toast.LENGTH_SHORT).show();
            }
        });
        rvVideoManager.setAdapter(sectionVideoAdapter);
    }

    public void createDummyData() {
        lstSectionVideo = new ArrayList<SectionVideo>();
        for (int i = 1; i <= 5; i++) {
            SectionVideo sv = new SectionVideo();
            sv.setDate("11/11/2017");
            ArrayList<SingleVideo> singleItem = new ArrayList<SingleVideo>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleVideo("http://api.livestar.vn//uploads/user/avatar_crop/80751/avatar_crop.jpg ", "XXXX", 1233, 14234));
            }
            sv.setLstVideo(singleItem);
            lstSectionVideo.add(sv);
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
        return R.layout.activity_video_manager;
    }

    @Override
    public void onClick() {

    }
}
