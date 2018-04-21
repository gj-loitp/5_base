package vn.loitp.app.activity.api.truyentranhtuan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.api.truyentranhtuan.helper.ComicUtils;
import vn.loitp.app.activity.api.truyentranhtuan.helper.comiclist.GetComicTask;
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic;
import vn.loitp.app.activity.api.truyentranhtuan.model.comictype.ComicType;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

public class TTTAPIComicListActivity extends BaseActivity {
    private TextView tvTitle;
    private TextView tv;
    private AVLoadingIndicatorView avi;
    private Button btSelect;

    private List<ComicType> comicTypeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btSelect = (Button) findViewById(R.id.bt_select);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tv = (TextView) findViewById(R.id.tv);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.hide();

        comicTypeList = ComicUtils.getComicTypeList();

        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSelect();
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
        return R.layout.activity_api_ttt_comic_list;
    }

    private void showDialogSelect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Chọn thể loại:");

        String[] items = new String[comicTypeList.size()];
        for (int i = 0; i < comicTypeList.size(); i++) {
            //LLog.d(TAG, i + " : " + photosetList.get(i).getTitle().getContent());
            items[i] = comicTypeList.get(i).getType();
        }

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                LLog.d(TAG, "onClick " + position);
                tv.setText("");
                tvTitle.setText("");
                new GetComicTask(activity, comicTypeList.get(position).getUrl(), avi, new GetComicTask.Callback() {
                    @Override
                    public void onSuccess(List<Comic> comicList) {
                        LUIUtil.printBeautyJson(comicList, tv);
                        tvTitle.setText("Danh sách truyện: " + comicList.size());
                    }

                    @Override
                    public void onError() {
                        ToastUtils.showShort("Error");
                    }
                }).execute();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
