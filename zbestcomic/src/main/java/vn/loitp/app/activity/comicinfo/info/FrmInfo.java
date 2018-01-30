package vn.loitp.app.activity.comicinfo.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

import loitp.basemaster.R;
import vn.loitp.app.data.ComicInfoData;
import vn.loitp.app.model.chap.Info;
import vn.loitp.core.base.BaseFragment;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.textview.textdecorator.lib.TextDecorator;

/**
 * Created by www.muathu@gmail.com on 7/26/2017.
 */

public class FrmInfo extends BaseFragment {
    private AdView adView;
    private TextView tvInfo;

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
        View view = inflater.inflate(R.layout.frm_comic_info, container, false);
        adView = (AdView) view.findViewById(R.id.adView);
        LUIUtil.createAdBanner(adView);

        tvInfo = (TextView) view.findViewById(R.id.tv_info);
        String author = "Tác giả: ";
        String otherName = "Tên khác: ";
        String summary = "Nội dung: ";
        String type = "Thể loại: ";
        String newChap = "Chương mới nhất: ";
        Info info = ComicInfoData.getInstance().getTttChap().getInfo();
        if (info != null) {
            final String text = author + info.getAuthor() + "\n\n" +
                    otherName + info.getOtherName() + "\n\n"
                    + summary + info.getSummary() + "\n\n"
                    + type + info.getType() + "\n\n"
                    + newChap + info.getNewChap();

            TextDecorator
                    .decorate(tvInfo, text)
                    .setTextColor(R.color.Black, author, otherName, summary, type, newChap)
                    //.setBackgroundColor(R.color.colorPrimary, "dolor", "elit")
                    //.strikethrough("Duis", "Praesent")
                    //.underline("sodales", "quam")
                    //.setSubscript("vitae")
                    /*.makeTextClickable(new OnTextClickListener() {
                        @Override
                        public void onClick(View view, String text) {
                            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
                        }
                    }, false, "porta", "commodo", "tempor venenatis nulla")*/
                    //.setTextColor(android.R.color.holo_green_light, "porta", "commodo", "tempor venenatis nulla")
                    .build();

        }
        return view;
    }

    @Override
    public void onPause() {
        adView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        adView.resume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        adView.destroy();
        super.onDestroy();
    }
}