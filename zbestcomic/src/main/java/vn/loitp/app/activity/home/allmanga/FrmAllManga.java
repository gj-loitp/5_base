package vn.loitp.app.activity.home.allmanga;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.helper.ComicUtils;
import vn.loitp.app.helper.comiclist.GetComicTask;
import vn.loitp.app.model.comic.Comic;
import vn.loitp.app.model.comictype.ComicType;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.utils.util.ToastUtils;
import vn.loitp.views.progressloadingview.avloadingindicatorview.lib.avi.AVLoadingIndicatorView;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmAllManga extends BaseFragment {
    private final String TAG = getClass().getSimpleName();
    private TextView tvTitle;
    private TextView tv;
    private AVLoadingIndicatorView avi;
    private Button btSelect;

    private List<ComicType> comicTypeList;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_all_manga, container, false);
        btSelect = (Button) view.findViewById(R.id.bt_select);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tv = (TextView) view.findViewById(R.id.tv);
        avi = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        avi.hide();

        comicTypeList = ComicUtils.getComicTypeList();

        btSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSelect();
            }
        });
        return view;
    }

    private void showDialogSelect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                new GetComicTask(getActivity(), comicTypeList.get(position).getUrl(), avi, new GetComicTask.Callback() {
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