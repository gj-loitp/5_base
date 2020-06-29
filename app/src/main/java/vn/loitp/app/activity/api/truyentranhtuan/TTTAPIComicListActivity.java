package vn.loitp.app.activity.api.truyentranhtuan;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import vn.loitp.app.R;
import vn.loitp.app.activity.api.truyentranhtuan.helper.ComicUtils;
import vn.loitp.app.activity.api.truyentranhtuan.helper.comiclist.GetComicTask;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.app.activity.api.truyentranhtuan.model.comictype.ComicType;

public class TTTAPIComicListActivity extends BaseFontActivity {
    private TextView tvTitle;
    private TextView tv;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private List<ComicType> comicTypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button btSelect = findViewById(R.id.bt_select);
        tvTitle = findViewById(R.id.tvTitle);
        tv = findViewById(R.id.textView);
        avLoadingIndicatorView = findViewById(R.id.indicatorView);
        avLoadingIndicatorView.hide();

        comicTypeList = ComicUtils.getComicTypeList();

        btSelect.setOnClickListener(v -> showDialogSelect());
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
        return R.layout.activity_api_ttt_comic_list;
    }

    private void showDialogSelect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Chọn thể loại:");

        String[] items = new String[comicTypeList.size()];
        for (int i = 0; i < comicTypeList.size(); i++) {
            //LLog.d(TAG, i + " : " + photosetList.get(i).getTitle().getContent());
            items[i] = comicTypeList.get(i).getType();
        }

        builder.setItems(items, (dialog, position) -> {
            LLog.d(getTAG(), "onClick " + position);
            tv.setText("");
            tvTitle.setText("");
            new GetComicTask(getActivity(), comicTypeList.get(position).getUrl(), avLoadingIndicatorView, new GetComicTask.Callback() {
                @Override
                public void onSuccess(List<Comic> comicList) {
                    LUIUtil.INSTANCE.printBeautyJson(comicList, tv);
                    tvTitle.setText("Danh sách truyện: " + comicList.size());
                }

                @Override
                public void onError() {
                    showShort("Error");
                }
            }).execute();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
