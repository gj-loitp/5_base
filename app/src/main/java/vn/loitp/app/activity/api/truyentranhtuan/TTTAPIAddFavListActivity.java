package vn.loitp.app.activity.api.truyentranhtuan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import vn.loitp.app.activity.api.truyentranhtuan.helper.favlist.AddComicFavListTask;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.core.base.BaseActivity;
import loitp.basemaster.R;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class TTTAPIAddFavListActivity extends BaseActivity {
    private TextView tv;
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.hide();

        findViewById(R.id.bt_add_vuongphongloi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comic comic = new Comic();
                comic.setDate("29.07.2014");
                comic.setUrl("http://truyentranhtuan.com/vuong-phong-loi-i/");
                comic.setTitle("Vương Phong Lôi I");
                addComic(comic);
            }
        });
        findViewById(R.id.bt_add_layers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comic comic = new Comic();
                comic.setDate("28.06.2015");
                comic.setUrl("http://truyentranhtuan.com/layers/");
                comic.setTitle("Layers");
                addComic(comic);
            }
        });
        findViewById(R.id.bt_add_blackhaze).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comic comic = new Comic();
                comic.setDate("12.03.2017");
                comic.setUrl("http://truyentranhtuan.com/black-haze/");
                comic.setTitle("Black Haze");
                addComic(comic);
            }
        });
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
        return R.layout.activity_api_ttt_add_fav_list;
    }

    private void addComic(Comic comic) {
        avi.smoothToShow();
        new AddComicFavListTask(activity, comic, new AddComicFavListTask.Callback() {
            @Override
            public void onAddComicSuccess(Comic mComic, List<Comic> comicList) {
                LUIUtil.printBeautyJson(comicList, tv);
                ToastUtils.showShort("onAddComicSuccess");
                avi.smoothToHide();
            }

            @Override
            public void onComicIsExist(Comic mComic, List<Comic> comicList) {
                LUIUtil.printBeautyJson(comicList, tv);
                ToastUtils.showShort("onComicIsExist");
                avi.smoothToHide();
            }

            @Override
            public void onAddComicError() {
                ToastUtils.showShort("onAddComicError");
                tv.setText("add error");
                avi.smoothToHide();
            }
        }).execute();
    }
}
