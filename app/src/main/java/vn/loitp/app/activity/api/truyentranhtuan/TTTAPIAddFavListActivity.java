package vn.loitp.app.activity.api.truyentranhtuan;

import android.os.Bundle;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.views.LToast;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import vn.loitp.app.R;
import vn.loitp.app.activity.api.truyentranhtuan.helper.favlist.AddComicFavListTask;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;

public class TTTAPIAddFavListActivity extends BaseFontActivity {
    private TextView tv;
    private AVLoadingIndicatorView avLoadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = findViewById(R.id.tv);
        avLoadingIndicatorView = findViewById(R.id.avi);
        avLoadingIndicatorView.hide();

        findViewById(R.id.bt_add_vuongphongloi).setOnClickListener(v -> {
            Comic comic = new Comic();
            comic.setDate("29.07.2014");
            comic.setUrl("http://truyentranhtuan.com/vuong-phong-loi-i/");
            comic.setTitle("Vương Phong Lôi I");
            addComic(comic);
        });
        findViewById(R.id.bt_add_layers).setOnClickListener(v -> {
            Comic comic = new Comic();
            comic.setDate("28.06.2015");
            comic.setUrl("http://truyentranhtuan.com/layers/");
            comic.setTitle("Layers");
            addComic(comic);
        });
        findViewById(R.id.bt_add_blackhaze).setOnClickListener(v -> {
            Comic comic = new Comic();
            comic.setDate("12.03.2017");
            comic.setUrl("http://truyentranhtuan.com/black-haze/");
            comic.setTitle("Black Haze");
            addComic(comic);
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
        avLoadingIndicatorView.smoothToShow();
        new AddComicFavListTask(getActivity(), comic, new AddComicFavListTask.Callback() {
            @Override
            public void onAddComicSuccess(Comic mComic, List<Comic> comicList) {
                LUIUtil.INSTANCE.printBeautyJson(comicList, tv);
                LToast.showShort(activity, "onAddComicSuccess", R.drawable.l_bkg_horizontal);
                avLoadingIndicatorView.smoothToHide();
            }

            @Override
            public void onComicIsExist(Comic mComic, List<Comic> comicList) {
                LUIUtil.INSTANCE.printBeautyJson(comicList, tv);
                LToast.showShort(activity, "onComicIsExist");
                avLoadingIndicatorView.smoothToHide();
            }

            @Override
            public void onAddComicError() {
                LToast.showShort(activity, "onAddComicError");
                tv.setText("add error");
                avLoadingIndicatorView.smoothToHide();
            }
        }).execute();
    }
}
