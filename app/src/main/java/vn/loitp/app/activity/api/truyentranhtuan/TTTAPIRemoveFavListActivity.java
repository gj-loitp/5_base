package vn.loitp.app.activity.api.truyentranhtuan;

import android.os.Bundle;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.views.LToast;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import vn.loitp.app.R;
import vn.loitp.app.activity.api.truyentranhtuan.helper.favlist.RemoveComicFavListTask;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;

public class TTTAPIRemoveFavListActivity extends BaseFontActivity {
    private TextView tv;
    private AVLoadingIndicatorView avLoadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = findViewById(R.id.tv);
        avLoadingIndicatorView = findViewById(R.id.indicatorView);
        avLoadingIndicatorView.hide();

        findViewById(R.id.bt_add_vuongphongloi).setOnClickListener(v -> {
            Comic comic = new Comic();
            comic.setDate("29.07.2014");
            comic.setUrl("http://truyentranhtuan.com/vuong-phong-loi-i/");
            comic.setTitle("Vương Phong Lôi I");
            removeComic(comic);
        });
        findViewById(R.id.bt_add_layers).setOnClickListener(v -> {
            Comic comic = new Comic();
            comic.setDate("28.06.2015");
            comic.setUrl("http://truyentranhtuan.com/layers/");
            comic.setTitle("Layers");
            removeComic(comic);
        });
        findViewById(R.id.bt_add_blackhaze).setOnClickListener(v -> {
            Comic comic = new Comic();
            comic.setDate("12.03.2017");
            comic.setUrl("http://truyentranhtuan.com/black-haze/");
            comic.setTitle("Black Haze");
            removeComic(comic);
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
        return R.layout.activity_api_ttt_remove_fav_list;
    }

    private void removeComic(Comic comic) {
        avLoadingIndicatorView.smoothToShow();
        new RemoveComicFavListTask(getActivity(), comic, new RemoveComicFavListTask.Callback() {
            @Override
            public void onRemoveComicSuccess(Comic mComic, List<Comic> comicList) {
                LToast.showShort(activity, "onRemoveComicSuccess");
                LUIUtil.INSTANCE.printBeautyJson(comicList, tv);
                avLoadingIndicatorView.smoothToHide();
            }

            @Override
            public void onComicIsNotExist(Comic mComic, List<Comic> comicList) {
                LToast.showShort(activity, "onComicIsNotExist");
                LUIUtil.INSTANCE.printBeautyJson(comicList, tv);
                avLoadingIndicatorView.smoothToHide();
            }

            @Override
            public void onRemoveComicError() {
                LToast.showShort(activity, "onRemoveComicError");
                avLoadingIndicatorView.smoothToHide();
            }
        }).execute();
    }

}
