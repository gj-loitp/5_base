package vn.loitp.app.activity.customviews.layout.scrollview2d;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.views.LToast;
import com.views.scrollview.LHorizontalScrollView;
import com.views.scrollview.LScrollView;
import com.views.scrollview.TwoDScrollView;

import loitp.basemaster.R;

public class ScrollView2DAdvanceActivity extends BaseFontActivity {
    private final int WIDTH_PX = 300;
    private final int HEIGHT_PX = 150;
    private TextView tvInfo;
    private ProgressBar pb;
    private LinearLayout vg1;
    private LHorizontalScrollView vg2;
    private LinearLayout ll2;
    private LScrollView vg3;
    private LinearLayout ll3;
    private TwoDScrollView vg4;
    private RelativeLayout rl4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rl4 = findViewById(R.id.rl_4);
        ll2 = findViewById(R.id.ll_2);
        ll3 = findViewById(R.id.ll_3);
        tvInfo = findViewById(R.id.tv_info);
        pb = findViewById(R.id.pb);
        vg1 = findViewById(R.id.vg_1);
        vg2 = findViewById(R.id.vg_2);
        vg3 = findViewById(R.id.vg_3);
        vg4 = findViewById(R.id.vg_4);

        vg2.setOnScrollListener((view, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            LLog.d(getTAG(), "vg2 setOnScrollListener " + scrollX);
            vg4.scrollTo(scrollX, vg4.getScrollY());
        });

        vg3.setOnScrollListener((view, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            LLog.d(getTAG(), "vg3 setOnScrollListener " + scrollX);
            vg4.scrollTo(vg4.getScrollX(), scrollY);
        });

        vg4.setScrollChangeListner((view, x, y, oldx, oldy) -> {
            tvInfo.setText("setScrollChangeListner " + x + " - " + y);
            LLog.d(getTAG(), "vg4 setOnScrollListener " + x);
            vg2.scrollTo(x, vg2.getScrollY());
            vg3.scrollTo(vg3.getScrollX(), y);
        });

        final Button btGenLine = findViewById(R.id.bt_gen_line);
        btGenLine.setOnClickListener(view -> {
            if (btGenLine.isClickable()) {
                btGenLine.setClickable(false);
                btGenLine.setTextColor(Color.GRAY);

                LUIUtil.INSTANCE.setSize(vg1, WIDTH_PX, HEIGHT_PX);
                LUIUtil.INSTANCE.setSize(vg2, ViewGroup.LayoutParams.MATCH_PARENT, HEIGHT_PX);
                LUIUtil.INSTANCE.setSize(vg3, WIDTH_PX, ViewGroup.LayoutParams.MATCH_PARENT);
                LUIUtil.INSTANCE.setSize(vg4, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                new Render(30, 24).execute();
                //new Render(7, 12).execute();
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
        return R.layout.activity_scrollview_2d_advance;
    }

    private class Render extends AsyncTask<Void, View, Void> {
        private int column;
        private int row;

        Render(final int column, final int row) {
            this.column = column;
            this.row = row;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LLog.d(getTAG(), "onPreExecute");
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            LLog.d(getTAG(), "doInBackground");
            genLine(column, row);
            return null;
        }

        @Override
        protected void onProgressUpdate(View... values) {
            super.onProgressUpdate(values);
            LLog.d(getTAG(), "onProgressUpdate " + System.currentTimeMillis());
            final View child = values[0];
            final ViewGroup parent = (ViewGroup) values[1];
            parent.addView(child);
        }

        private void sleep(final int mls) {
            try {
                Thread.sleep(mls);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void genLine(final int column, final int row) {
            //gen view group 2
            for (int i = 0; i < column; i++) {
                final Button button = new Button(getActivity());
                button.setLayoutParams(new LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX));
                button.setText("Date " + i);
                button.setOnClickListener(view1 -> {
                    LToast.showShort(getActivity(), "Click " + button.getText().toString(), R.drawable.l_bkg_horizontal);
                });
                publishProgress(button, ll2);
            }

            //gen view group 3
            for (int i = 0; i < row; i++) {
                final Button button = new Button(getActivity());
                button.setLayoutParams(new LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX));
                button.setText(i + ":00:00");
                button.setOnClickListener(view1 -> {
                    LToast.showShort(getActivity(), "Click " + button.getText().toString(), R.drawable.l_bkg_horizontal);
                });
                publishProgress(button, ll3);
            }

            //gen view group 4
            for (int i = 0; i < row; i++) {
                final LinearLayout linearLayout = new LinearLayout(getActivity());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0, HEIGHT_PX * i, 0, 0);
                linearLayout.setLayoutParams(layoutParams);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                for (int j = 0; j < column; j++) {
                    final Button button = new Button(getActivity());
                    button.setBackgroundResource(R.drawable.bg_square);
                    button.setLayoutParams(new LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX));
                    button.setText("Pos " + i + " - " + j);
                    button.setOnClickListener(view1 -> {
                        LToast.showShort(getActivity(), "Click " + button.getText().toString(), R.drawable.l_bkg_horizontal);
                    });
                    linearLayout.addView(button);
                }
                publishProgress(linearLayout, rl4);
            }

            //add sticker img
            final ImageView sticker0 = new ImageView(getActivity());
            sticker0.setImageResource(R.drawable.loitp);
            sticker0.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final RelativeLayout.LayoutParams rl0 = new RelativeLayout.LayoutParams(WIDTH_PX, HEIGHT_PX);
            rl0.setMargins(WIDTH_PX, HEIGHT_PX * 7, 0, 0);
            sticker0.setLayoutParams(rl0);
            publishProgress(sticker0, rl4);

            final ImageView sticker1 = new ImageView(getActivity());
            sticker1.setImageResource(R.drawable.loitp);
            sticker1.setScaleType(ImageView.ScaleType.CENTER_CROP);
            final RelativeLayout.LayoutParams rl1 = new RelativeLayout.LayoutParams((int) (WIDTH_PX * 2.5), (int) (HEIGHT_PX * 2.5));
            rl1.setMargins((int) (WIDTH_PX * 1.5), HEIGHT_PX * 2, 0, 0);
            sticker1.setLayoutParams(rl1);
            publishProgress(sticker1, rl4);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            LLog.d(getTAG(), "onPostExecute");
            pb.setVisibility(View.GONE);
        }
    }
}
