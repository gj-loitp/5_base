package vn.loitp.app.activity.api.truyentranhtuan;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.views.LToast;
import com.views.progressloadingview.avl.LAVLoadingIndicatorView;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.api.truyentranhtuan.helper.favlist.AddComicFavListTask;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;

public class TTTAPIAddFavListActivity extends BaseFontActivity {
    private TextView tv;
    private LAVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = (TextView) findViewById(R.id.tv);
        avi = (LAVLoadingIndicatorView) findViewById(R.id.avi);
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
        new AddComicFavListTask(getActivity(), comic, new AddComicFavListTask.Callback() {
            @Override
            public void onAddComicSuccess(Comic mComic, List<Comic> comicList) {
                LUIUtil.INSTANCE.printBeautyJson(comicList, tv);
                LToast.showShort(activity, "onAddComicSuccess", R.drawable.l_bkg_horizontal);
                avi.smoothToHide();
            }

            @Override
            public void onComicIsExist(Comic mComic, List<Comic> comicList) {
                LUIUtil.INSTANCE.printBeautyJson(comicList, tv);
                LToast.showShort(activity, "onComicIsExist");
                avi.smoothToHide();
            }

            @Override
            public void onAddComicError() {
                LToast.showShort(activity, "onAddComicError");
                tv.setText("add error");
                avi.smoothToHide();
            }
        }).execute();
    }
}
